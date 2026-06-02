package com.homestaybooking.view.homestay;

import com.homestaybooking.model.domain.Homestay;
import com.homestaybooking.model.domain.Room;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.List;

public class HomestayDetailView extends JDialog {
    public HomestayDetailView(Frame owner, Homestay homestay, List<Room> rooms) {
        super(owner, "Chi tiet homestay", true);
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.append("Ten: " + homestay.name + "\n");
        area.append("Dia chi: " + homestay.address + "\n");
        area.append("Loai: " + homestay.type + "\n");
        area.append("Trang thai: " + homestay.status + "\n");
        area.append("Mo ta: " + homestay.description + "\n\n");
        area.append("Phong:\n");
        for (Room room : rooms) {
            area.append("- " + room + "\n");
        }
        add(new JScrollPane(area), BorderLayout.CENTER);
        setSize(520, 360);
        setLocationRelativeTo(owner);
    }
}
