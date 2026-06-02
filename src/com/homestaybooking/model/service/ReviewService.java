package com.homestaybooking.model.service;

import com.homestaybooking.model.domain.Booking;
import com.homestaybooking.model.domain.Review;
import com.homestaybooking.model.domain.User;
import com.homestaybooking.model.enums.BookingStatus;
import com.homestaybooking.model.repository.BookingRepository;
import com.homestaybooking.model.repository.ReviewRepository;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final UserService userService;

    public ReviewService(ReviewRepository reviewRepository, BookingRepository bookingRepository, UserService userService) {
        this.reviewRepository = reviewRepository;
        this.bookingRepository = bookingRepository;
        this.userService = userService;
    }

    public List<Review> listByRoom(String roomId) {
        return reviewRepository.findByRoom(roomId);
    }

    public Review create(String bookingId, int rating, String comment) {
        User user = userService.requireLogin();
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new IllegalArgumentException("Khong tim thay booking"));
        if (!booking.bookedBy.equals(user.id)) {
            throw new IllegalStateException("Chi nguoi dat phong moi duoc danh gia");
        }
        if (booking.status != BookingStatus.COMPLETED) {
            throw new IllegalStateException("Chi booking da hoan tat moi duoc danh gia");
        }
        if (reviewRepository.findByBooking(bookingId).isPresent()) {
            throw new IllegalStateException("Booking nay da co danh gia");
        }
        validateRating(rating);
        return reviewRepository.save(new Review(null, bookingId, rating, comment, LocalDateTime.now()));
    }

    public Review update(String reviewId, int rating, String comment) {
        validateRating(rating);
        Review review = reviewRepository.findAll().stream()
                .filter(item -> item.id.equals(reviewId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Khong tim thay danh gia"));
        review.rating = rating;
        review.comment = comment;
        review.reviewDate = LocalDateTime.now();
        return reviewRepository.save(review);
    }

    private void validateRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating phai tu 1 den 5");
        }
    }
}
