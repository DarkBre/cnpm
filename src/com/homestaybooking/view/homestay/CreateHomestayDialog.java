package com.homestaybooking.view.homestay;

import com.homestaybooking.controller.HomestayController;
import com.homestaybooking.view.CreateDialog;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Frame;

public class CreateHomestayDialog extends CreateDialog {
    public CreateHomestayDialog(Frame owner, HomestayController homestayController, Runnable onDone) {
        super(owner, "Tao homestay");
        JTextField name = text("Ten");
        JTextField address = text("Dia chi");
        JTextField type = text("Loai");
        JTextArea description = area("Mo ta");
        JButton create = button("Tao");
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
