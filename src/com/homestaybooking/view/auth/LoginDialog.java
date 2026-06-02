package com.homestaybooking.view.auth;

import com.homestaybooking.controller.UserController;
import com.homestaybooking.view.CreateDialog;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Frame;

public class LoginDialog extends CreateDialog {
    public LoginDialog(Frame owner, UserController userController, Runnable onDone) {
        super(owner, "Đăng nhập");
        JTextField email = text("Email");
        JPasswordField password = password("Mật khẩu");
        JButton login = button("Đăng nhập");
        login.addActionListener(event -> {
            try {
                userController.login(email.getText(), new String(password.getPassword()));
                dispose();
                onDone.run();
            } catch (Exception exception) {
                showError(exception);
            }
        });
    }
}
