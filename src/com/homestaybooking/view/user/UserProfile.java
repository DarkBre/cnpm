package com.homestaybooking.view.user;

import com.homestaybooking.controller.UserController;
import com.homestaybooking.model.domain.User;
import com.homestaybooking.view.CreateDialog;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Frame;

public class UserProfile extends CreateDialog {
    public UserProfile(Frame owner, UserController userController, Runnable onDone) {
        super(owner, "Hồ sơ cá nhân");
        User user = userController.currentUser();
        JTextField fullname = text("Họ tên");
        JTextField email = text("Email");
        JTextField phone = text("Số điện thoại");
        JTextField dob = text("Ngày sinh yyyy-MM-dd");
        JTextField role = text("Vai trò");
        if (user != null) {
            fullname.setText(user.fullname);
            email.setText(user.email);
            phone.setText(user.phone);
            dob.setText(user.dob == null ? "" : user.dob.toString());
            role.setText(user.role.name());
            role.setEditable(false);
        }
        JButton save = button("Lưu");
        save.addActionListener(event -> {
            try {
                userController.updateProfile(fullname.getText(), email.getText(), phone.getText(), parseDate(dob));
                dispose();
                onDone.run();
            } catch (Exception exception) {
                showError(exception);
            }
        });
    }
}
