package com.homestaybooking.view.user;

import com.homestaybooking.controller.UserController;
import com.homestaybooking.model.domain.User;
import com.homestaybooking.view.CreateDialog;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Frame;

public class UserProfile extends CreateDialog {
    public UserProfile(Frame owner, UserController userController, Runnable onDone) {
        super(owner, "Ho so ca nhan");
        User user = userController.currentUser();
        JTextField fullname = text("Ho ten");
        JTextField email = text("Email");
        JTextField phone = text("So dien thoai");
        JTextField dob = text("Ngay sinh yyyy-MM-dd");
        JTextField role = text("Role");
        if (user != null) {
            fullname.setText(user.fullname);
            email.setText(user.email);
            phone.setText(user.phone);
            dob.setText(user.dob == null ? "" : user.dob.toString());
            role.setText(user.role.name());
            role.setEditable(false);
        }
        JButton save = button("Luu");
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
