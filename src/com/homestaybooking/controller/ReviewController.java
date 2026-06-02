package com.homestaybooking.controller;

import com.homestaybooking.model.domain.Review;
import com.homestaybooking.model.service.ReviewService;

import java.util.List;

public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public List<Review> byRoom(String roomId) {
        return reviewService.listByRoom(roomId);
    }

    public Review create(String bookingId, int rating, String comment) {
        return reviewService.create(bookingId, rating, comment);
    }

    public Review update(String reviewId, int rating, String comment) {
        return reviewService.update(reviewId, rating, comment);
    }
}
