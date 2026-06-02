package com.homestaybooking.view.review;

import com.homestaybooking.controller.ReviewController;
import com.homestaybooking.view.CreateDialog;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Frame;

public class CreateReviewDialog extends CreateDialog {
    public CreateReviewDialog(Frame owner, ReviewController reviewController, Runnable onDone) {
        super(owner, "Tạo đánh giá");
        JTextField bookingId = text("Booking ID");
        JTextField rating = text("Rating 1-5");
        JTextArea comment = area("Nhận xét");
        JButton create = button("Tạo đánh giá");
        create.addActionListener(event -> {
            try {
                reviewController.create(bookingId.getText(), parseInt(rating), comment.getText());
                dispose();
                onDone.run();
            } catch (Exception exception) {
                showError(exception);
            }
        });
    }
}
