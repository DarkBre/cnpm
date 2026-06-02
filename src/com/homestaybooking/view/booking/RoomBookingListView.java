package com.homestaybooking.view.booking;

import com.homestaybooking.controller.BookingController;
import com.homestaybooking.model.domain.Booking;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.List;

public class RoomBookingListView extends JDialog {
    public RoomBookingListView(Frame owner, BookingController bookingController, List<Booking> bookings, Runnable onDone) {
        super(owner, "Danh sách booking", true);
        JTextArea area = new JTextArea();
        area.setEditable(false);
        for (Booking booking : bookings) {
            area.append(booking.id + " | " + booking + "\n");
        }
        if (bookings.isEmpty()) {
            area.setText("Chưa có booking.");
        }
        JButton cancel = new JButton("Hủy theo ID");
        JButton complete = new JButton("Hoàn tất theo ID");
        cancel.addActionListener(event -> handle(owner, bookingController, true, onDone));
        complete.addActionListener(event -> handle(owner, bookingController, false, onDone));
        JPanel actions = new JPanel();
        actions.add(cancel);
        actions.add(complete);
        add(new JScrollPane(area), BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);
        setSize(620, 360);
        setLocationRelativeTo(owner);
    }

    private void handle(Frame owner, BookingController bookingController, boolean cancel, Runnable onDone) {
        String id = JOptionPane.showInputDialog(this, "Nhập Booking ID");
        if (id == null || id.isBlank()) {
            return;
        }
        try {
            if (cancel) {
                bookingController.cancel(id.trim());
            } else {
                bookingController.complete(id.trim());
            }
            onDone.run();
            dispose();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(owner, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
