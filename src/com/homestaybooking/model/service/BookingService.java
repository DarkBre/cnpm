package com.homestaybooking.model.service;

import com.homestaybooking.model.domain.Booking;
import com.homestaybooking.model.domain.Homestay;
import com.homestaybooking.model.domain.Room;
import com.homestaybooking.model.domain.User;
import com.homestaybooking.model.enums.BookingStatus;
import com.homestaybooking.model.enums.UserRole;
import com.homestaybooking.model.repository.BookingRepository;
import com.homestaybooking.model.repository.HomestayRepository;
import com.homestaybooking.model.repository.RoomRepository;
import com.homestaybooking.model.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BookingService {
    private static final long PRICE_PER_NIGHT = 500000;
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final HomestayRepository homestayRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public BookingService(BookingRepository bookingRepository, RoomRepository roomRepository, HomestayRepository homestayRepository, UserRepository userRepository, UserService userService) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.homestayRepository = homestayRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public Booking create(String roomId, LocalDate checkIn, LocalDate checkOut) {
        User user = userService.requireLogin();
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new IllegalArgumentException("Khong tim thay phong"));
        validateRange(checkIn, checkOut);
        if (!"AVAILABLE".equals(room.status)) {
            throw new IllegalStateException("Phong khong san sang de dat");
        }
        if (bookingRepository.hasOverlap(roomId, checkIn, checkOut)) {
            throw new IllegalStateException("Phong da co booking trong khoang ngay nay");
        }
        int nights = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
        long amount = nights * PRICE_PER_NIGHT;
        if (user.balance < amount) {
            throw new IllegalStateException("So du vi khong du");
        }
        userRepository.updateBalance(user.id, user.balance - amount);
        user.balance -= amount;
        Booking booking = new Booking(null, user.id, roomId, checkIn, checkOut, nights, amount, 0, amount, BookingStatus.CONFIRMED);
        return bookingRepository.save(booking);
    }

    public List<Booking> myBookings() {
        User user = userService.requireLogin();
        return bookingRepository.findByUser(user.id);
    }

    public List<Booking> ownerBookings() {
        User user = userService.requireLogin();
        if (user.role == UserRole.ADMIN) {
            return bookingRepository.findAll();
        }
        List<String> homestayIds = homestayRepository.findByOwner(user.id).stream().map(homestay -> homestay.id).toList();
        List<String> roomIds = roomRepository.findAll("").stream().filter(room -> homestayIds.contains(room.homestayId)).map(room -> room.id).toList();
        return bookingRepository.findByRooms(roomIds);
    }

    public Booking detail(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new IllegalArgumentException("Khong tim thay booking"));
        requireCanView(booking);
        return booking;
    }

    public Booking cancel(String bookingId) {
        Booking booking = detail(bookingId);
        User user = userService.requireLogin();
        if (!booking.bookedBy.equals(user.id) && user.role != UserRole.ADMIN) {
            throw new IllegalStateException("Chi nguoi dat phong hoac admin duoc huy booking");
        }
        if (booking.status == BookingStatus.CANCELLED || booking.status == BookingStatus.COMPLETED) {
            throw new IllegalStateException("Booking khong the huy");
        }
        long daysBeforeCheckIn = ChronoUnit.DAYS.between(LocalDate.now(), booking.checkInDate);
        if (daysBeforeCheckIn <= 0) {
            throw new IllegalStateException("Khong the huy vao hoac sau ngay check-in");
        }
        long fee = daysBeforeCheckIn >= 7 ? 0 : daysBeforeCheckIn >= 3 ? booking.totalAmount * 30 / 100 : booking.totalAmount * 50 / 100;
        long refund = booking.totalAmount - fee;
        booking.status = BookingStatus.CANCELLED;
        booking.cancellationFee = fee;
        booking.refundAmount = refund;
        booking.cancelledAt = LocalDateTime.now();
        User bookedUser = userRepository.findById(booking.bookedBy).orElseThrow();
        userRepository.updateBalance(bookedUser.id, bookedUser.balance + refund);
        bookedUser.balance += refund;
        return bookingRepository.save(booking);
    }

    public Booking complete(String bookingId) {
        Booking booking = detail(bookingId);
        User user = userService.requireLogin();
        if (booking.status != BookingStatus.CONFIRMED) {
            throw new IllegalStateException("Chi booking CONFIRMED moi duoc hoan tat");
        }
        Room room = roomRepository.findById(booking.roomId).orElseThrow();
        Homestay homestay = homestayRepository.findById(room.homestayId).orElseThrow();
        if (user.role != UserRole.ADMIN && !homestay.ownerId.equals(user.id)) {
            throw new IllegalStateException("Chi owner cua phong hoac admin duoc hoan tat booking");
        }
        booking.status = BookingStatus.COMPLETED;
        User owner = userRepository.findById(homestay.ownerId).orElseThrow();
        userRepository.updateBalance(owner.id, owner.balance + booking.totalAmount);
        owner.balance += booking.totalAmount;
        return bookingRepository.save(booking);
    }

    private void requireCanView(Booking booking) {
        User user = userService.requireLogin();
        if (user.role == UserRole.ADMIN || booking.bookedBy.equals(user.id)) {
            return;
        }
        Room room = roomRepository.findById(booking.roomId).orElseThrow();
        Homestay homestay = homestayRepository.findById(room.homestayId).orElseThrow();
        if (!homestay.ownerId.equals(user.id)) {
            throw new IllegalStateException("Khong co quyen xem booking nay");
        }
    }

    private void validateRange(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null || !checkIn.isBefore(checkOut)) {
            throw new IllegalArgumentException("Ngay check-in phai truoc ngay check-out");
        }
    }
}
