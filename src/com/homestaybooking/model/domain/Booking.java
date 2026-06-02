package com.homestaybooking.model.domain;

import com.homestaybooking.model.enums.BookingStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Booking {
    public String id;
    public String bookedBy;
    public String roomId;
    public LocalDate checkInDate;
    public LocalDate checkOutDate;
    public int totalNights;
    public long originalAmount;
    public long discountAmount;
    public long totalAmount;
    public long cancellationFee;
    public long refundAmount;
    public LocalDateTime cancelledAt;
    public BookingStatus status;

    public Booking() {
    }

    public Booking(String id, String bookedBy, String roomId, LocalDate checkInDate, LocalDate checkOutDate, int totalNights, long originalAmount, long discountAmount, long totalAmount, BookingStatus status) {
        this.id = id;
        this.bookedBy = bookedBy;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalNights = totalNights;
        this.originalAmount = originalAmount;
        this.discountAmount = discountAmount;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    @Override
    public String toString() {
        return roomId + " | " + checkInDate + " -> " + checkOutDate + " | " + status;
    }
}
