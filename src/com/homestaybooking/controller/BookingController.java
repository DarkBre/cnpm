package com.homestaybooking.controller;

import com.homestaybooking.model.domain.Booking;
import com.homestaybooking.model.service.BookingService;

import java.time.LocalDate;
import java.util.List;

public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public Booking create(String roomId, LocalDate checkIn, LocalDate checkOut) {
        return bookingService.create(roomId, checkIn, checkOut);
    }

    public List<Booking> mine() {
        return bookingService.myBookings();
    }

    public List<Booking> ownerBookings() {
        return bookingService.ownerBookings();
    }

    public Booking detail(String bookingId) {
        return bookingService.detail(bookingId);
    }

    public Booking cancel(String bookingId) {
        return bookingService.cancel(bookingId);
    }

    public Booking complete(String bookingId) {
        return bookingService.complete(bookingId);
    }
}
