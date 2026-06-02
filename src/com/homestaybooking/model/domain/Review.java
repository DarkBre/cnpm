package com.homestaybooking.model.domain;

import java.time.LocalDateTime;

public class Review {
    public String id;
    public String bookingId;
    public int rating;
    public String comment;
    public LocalDateTime reviewDate;

    public Review() {
    }

    public Review(String id, String bookingId, int rating, String comment, LocalDateTime reviewDate) {
        this.id = id;
        this.bookingId = bookingId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    @Override
    public String toString() {
        return rating + "/5 - " + comment;
    }
}
