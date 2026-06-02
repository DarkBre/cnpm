package com.homestaybooking.view.booking;

import com.homestaybooking.controller.BookingController;
import com.homestaybooking.view.CreateDialog;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Frame;

public class CreateBookingDialog extends CreateDialog {
    public CreateBookingDialog(Frame owner, BookingController bookingController, String defaultRoomId, Runnable onDone) {
        super(owner, "Tao booking");
        JTextField roomId = text("Room ID");
        roomId.setText(defaultRoomId == null ? "" : defaultRoomId);
        JTextField checkIn = text("Check-in yyyy-MM-dd");
        JTextField checkOut = text("Check-out yyyy-MM-dd");
        JButton create = button("Dat phong");
        create.addActionListener(event -> {
            try {
                bookingController.create(roomId.getText(), parseDate(checkIn), parseDate(checkOut));
                dispose();
                onDone.run();
            } catch (Exception exception) {
                showError(exception);
            }
        });
    }
}
