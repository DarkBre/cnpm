package com.homestaybooking.view.homestay;

import com.homestaybooking.model.domain.Review;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.List;

public class HomestayReviewListView extends JDialog {
    public HomestayReviewListView(Frame owner, List<Review> reviews) {
        super(owner, "Danh sach danh gia", true);
        JTextArea area = new JTextArea();
        area.setEditable(false);
        for (Review review : reviews) {
            area.append(review + "\n");
        }
        if (reviews.isEmpty()) {
            area.setText("Chua co danh gia.");
        }
        add(new JScrollPane(area), BorderLayout.CENTER);
        setSize(420, 280);
        setLocationRelativeTo(owner);
    }
}
