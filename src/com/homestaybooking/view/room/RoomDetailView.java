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
        super(owner, "Chi tiet phong", true);
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.append("Ten: " + room.name + "\n");
        area.append("Loai: " + room.type + "\n");
        area.append("Suc chua: " + room.capacity + "\n");
        area.append("Trang thai: " + room.status + "\n");
        area.append("Mo ta: " + room.description + "\n\n");
        area.append("Danh gia:\n");
        for (Review review : reviews) {
            area.append("- " + review + "\n");
        }
        add(new JScrollPane(area), BorderLayout.CENTER);
        setSize(480, 340);
        setLocationRelativeTo(owner);
    }
}
