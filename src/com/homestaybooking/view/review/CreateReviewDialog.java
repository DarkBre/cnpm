package com.homestaybooking.view.review;

import com.homestaybooking.controller.ReviewController;
import com.homestaybooking.view.CreateDialog;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Frame;

public class CreateReviewDialog extends CreateDialog {
    public CreateReviewDialog(Frame owner, ReviewController reviewController, Runnable onDone) {
        super(owner, "Tao danh gia");
        JTextField bookingId = text("Booking ID");
        JTextField rating = text("Rating 1-5");
        JTextArea comment = area("Nhan xet");
        JButton create = button("Tao danh gia");
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
