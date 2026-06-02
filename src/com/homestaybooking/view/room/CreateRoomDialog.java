package com.homestaybooking.view.room;

import com.homestaybooking.controller.RoomController;
import com.homestaybooking.view.CreateDialog;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Frame;

public class CreateRoomDialog extends CreateDialog {
    public CreateRoomDialog(Frame owner, RoomController roomController, Runnable onDone) {
        super(owner, "Tao phong");
        JTextField homestayId = text("Homestay ID");
        JTextField name = text("Ten phong");
        JTextField type = text("Loai phong");
        JTextField capacity = text("Suc chua");
        JTextArea description = area("Mo ta");
        JButton create = button("Tao");
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
