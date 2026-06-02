package com.homestaybooking.view.room;

import com.homestaybooking.model.domain.Review;
import com.homestaybooking.model.domain.Room;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.List;

public class RoomDetailView extends JDialog {
    public RoomDetailView(Frame owner, Room room, List<Review> reviews) {
        super(owner, "Chi tiết phòng", true);
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.append("Tên: " + room.name + "\n");
        area.append("Loại: " + room.type + "\n");
        area.append("Sức chứa: " + room.capacity + "\n");
        area.append("Trạng thái: " + room.status + "\n");
        area.append("Mô tả: " + room.description + "\n\n");
        area.append("Đánh giá:\n");
        for (Review review : reviews) {
            area.append("- " + review + "\n");
        }
        add(new JScrollPane(area), BorderLayout.CENTER);
        setSize(480, 340);
        setLocationRelativeTo(owner);
    }
}
