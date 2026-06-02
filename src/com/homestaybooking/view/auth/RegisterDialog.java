package com.homestaybooking.view.auth;

import com.homestaybooking.controller.UserController;
import com.homestaybooking.view.CreateDialog;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Frame;

public class RegisterDialog extends CreateDialog {
    public RegisterDialog(Frame owner, UserController userController, Runnable onDone) {
        super(owner, "Dang ky");
        JTextField fullname = text("Ho ten");
        JTextField email = text("Email");
        JPasswordField password = password("Mat khau");
        JTextField phone = text("So dien thoai");
        JTextField dob = text("Ngay sinh yyyy-MM-dd");
        JButton register = button("Dang ky");
        register.addActionListener(event -> {
            try {
                userController.register(fullname.getText(), email.getText(), new String(password.getPassword()), phone.getText(), parseDate(dob));
                dispose();
                onDone.run();
            } catch (Exception exception) {
                showError(exception);
            }
        });
    }
}
