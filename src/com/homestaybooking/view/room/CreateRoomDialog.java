package com.homestaybooking.view.room;

import com.homestaybooking.controller.RoomController;
import com.homestaybooking.view.CreateDialog;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Frame;

public class CreateRoomDialog extends CreateDialog {
    public CreateRoomDialog(Frame owner, RoomController roomController, Runnable onDone) {
        super(owner, "Tạo phòng");
        JTextField homestayId = text("Homestay ID");
        JTextField name = text("Tên phòng");
        JTextField type = text("Loại phòng");
        JTextField capacity = text("Sức chứa");
        JTextArea description = area("Mô tả");
        JButton create = button("Tạo");
        create.addActionListener(event -> {
            try {
                roomController.create(homestayId.getText(), name.getText(), type.getText(), parseInt(capacity), description.getText());
                dispose();
                onDone.run();
            } catch (Exception exception) {
                showError(exception);
            }
        });
    }
}
