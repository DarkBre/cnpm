package com.homestaybooking.view.homestay;

import com.homestaybooking.controller.HomestayController;
import com.homestaybooking.view.CreateDialog;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Frame;

public class CreateHomestayDialog extends CreateDialog {
    public CreateHomestayDialog(Frame owner, HomestayController homestayController, Runnable onDone) {
        super(owner, "Tạo homestay");
        JTextField name = text("Tên");
        JTextField address = text("Địa chỉ");
        JTextField type = text("Loại");
        JTextArea description = area("Mô tả");
        JButton create = button("Tạo");
        create.addActionListener(event -> {
            try {
                homestayController.create(name.getText(), address.getText(), type.getText(), description.getText());
                dispose();
                onDone.run();
            } catch (Exception exception) {
                showError(exception);
            }
        });
    }
}
