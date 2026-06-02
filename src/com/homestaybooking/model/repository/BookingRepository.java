package com.homestaybooking.model.repository;

import com.homestaybooking.connection.Database;
import com.homestaybooking.model.domain.Booking;
import com.homestaybooking.model.enums.BookingStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class BookingRepository {
    private static final List<Booking> BOOKINGS = new ArrayList<>();

    static {
        BOOKINGS.add(new Booking("b-completed", "u-customer", "r-family", LocalDate.of(2026, 4, 1), LocalDate.of(2026, 4, 3), 2, 1000000, 0, 1000000, BookingStatus.COMPLETED));
        BOOKINGS.add(new Booking("b-confirmed", "u-customer", "r-pine", LocalDate.of(2026, 7, 10), LocalDate.of(2026, 7, 12), 2, 1000000, 0, 1000000, BookingStatus.CONFIRMED));
    }

    public List<Booking> findAll() {
        return List.copyOf(BOOKINGS);
    }

    public Optional<Booking> findById(String id) {
        return BOOKINGS.stream().filter(booking -> booking.id.equals(id)).findFirst();
    }

    public List<Booking> findByUser(String userId) {
        return BOOKINGS.stream().filter(booking -> booking.bookedBy.equals(userId)).toList();
    }

    public List<Booking> findByRooms(Collection<String> roomIds) {
        return BOOKINGS.stream().filter(booking -> roomIds.contains(booking.roomId)).toList();
    }

    public boolean hasOverlap(String roomId, LocalDate checkIn, LocalDate checkOut) {
        return BOOKINGS.stream()
                .filter(booking -> booking.roomId.equals(roomId))
                .filter(booking -> booking.status == BookingStatus.CONFIRMED || booking.status == BookingStatus.PENDING_PAYMENT)
                .anyMatch(booking -> checkIn.isBefore(booking.checkOutDate) && checkOut.isAfter(booking.checkInDate));
    }

    public Booking save(Booking booking) {
        if (booking.id == null || booking.id.isBlank()) {
            booking.id = Database.newId();
            BOOKINGS.add(booking);
            return booking;
        }
        for (int i = 0; i < BOOKINGS.size(); i++) {
            if (BOOKINGS.get(i).id.equals(booking.id)) {
                BOOKINGS.set(i, booking);
                return booking;
            }
        }
        BOOKINGS.add(booking);
        return booking;
    }
}
