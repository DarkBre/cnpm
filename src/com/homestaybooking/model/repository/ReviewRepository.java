package com.homestaybooking.model.repository;

import com.homestaybooking.connection.Database;
import com.homestaybooking.model.domain.Booking;
import com.homestaybooking.model.domain.Review;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReviewRepository {
    private static final List<Review> REVIEWS = new ArrayList<>();
    private final BookingRepository bookingRepository;

    public ReviewRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
        if (REVIEWS.isEmpty()) {
            REVIEWS.add(new Review("rev-1", "b-completed", 5, "Phong sach, vi tri dep.", LocalDateTime.now().minusDays(20)));
        }
    }

    public List<Review> findByRoom(String roomId) {
        return REVIEWS.stream()
                .filter(review -> bookingRepository.findById(review.bookingId).map(booking -> booking.roomId.equals(roomId)).orElse(false))
                .toList();
    }

    public Optional<Review> findByBooking(String bookingId) {
        return REVIEWS.stream().filter(review -> review.bookingId.equals(bookingId)).findFirst();
    }

    public Review save(Review review) {
        if (review.id == null || review.id.isBlank()) {
            review.id = Database.newId();
            REVIEWS.add(review);
            return review;
        }
        for (int i = 0; i < REVIEWS.size(); i++) {
            if (REVIEWS.get(i).id.equals(review.id)) {
                REVIEWS.set(i, review);
                return review;
            }
        }
        REVIEWS.add(review);
        return review;
    }

    public List<Review> findAll() {
        return List.copyOf(REVIEWS);
    }
}
