package com.homestaybooking.model.service;

import com.homestaybooking.model.domain.User;
import com.homestaybooking.model.enums.UserRole;
import com.homestaybooking.model.enums.UserStatus;
import com.homestaybooking.model.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

public class UserService {
    private static User currentUser;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Email hoac mat khau khong dung"));
        if (!user.password.equals(password)) {
            throw new IllegalArgumentException("Email hoac mat khau khong dung");
        }
        if (user.status == UserStatus.LOCKED) {
            throw new IllegalStateException("Tai khoan dang bi khoa");
        }
        currentUser = user;
        return user;
    }

    public User register(String fullname, String email, String password, String phone, LocalDate dob) {
        validateNameEmailPassword(fullname, email, password);
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email da ton tai");
        }
        User user = new User(null, fullname.trim(), email.trim(), password, phone, dob, UserRole.CUSTOMER, UserStatus.ACTIVE, 0);
        return userRepository.save(user);
    }

    public void logout() {
        currentUser = null;
    }

    public User currentUser() {
        return currentUser;
    }

    public User requireLogin() {
        if (currentUser == null) {
            throw new IllegalStateException("Can dang nhap de thuc hien chuc nang nay");
        }
        return currentUser;
    }

    public User requireAdmin() {
        User user = requireLogin();
        if (user.role != UserRole.ADMIN) {
            throw new IllegalStateException("Chuc nang nay yeu cau role ADMIN");
        }
        return user;
    }

    public List<User> listUsers(String keyword) {
        requireAdmin();
        return userRepository.findAll(keyword);
    }

    public User updateProfile(String fullname, String email, String phone, LocalDate dob) {
        User user = requireLogin();
        validateNameEmail(fullname, email);
        userRepository.findByEmail(email)
                .filter(existing -> !existing.id.equals(user.id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Email da duoc user khac su dung");
                });
        user.fullname = fullname.trim();
        user.email = email.trim();
        user.phone = phone;
        user.dob = dob;
        return userRepository.save(user);
    }

    public void changePassword(String oldPassword, String newPassword) {
        User user = requireLogin();
        if (!user.password.equals(oldPassword)) {
            throw new IllegalArgumentException("Mat khau cu khong dung");
        }
        if (newPassword == null || newPassword.length() < 6) {
            throw new IllegalArgumentException("Mat khau moi toi thieu 6 ky tu");
        }
        user.password = newPassword;
        userRepository.save(user);
    }

    public void lockUser(String userId) {
        requireAdmin();
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Khong tim thay user"));
        user.status = UserStatus.LOCKED;
        userRepository.save(user);
    }

    public void unlockUser(String userId) {
        requireAdmin();
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Khong tim thay user"));
        user.status = UserStatus.ACTIVE;
        userRepository.save(user);
    }

    public void promoteToOwner(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Khong tim thay user"));
        if (user.role == UserRole.CUSTOMER) {
            user.role = UserRole.OWNER;
            userRepository.save(user);
        }
    }

    private void validateNameEmailPassword(String fullname, String email, String password) {
        validateNameEmail(fullname, email);
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Mat khau toi thieu 6 ky tu");
        }
    }

    private void validateNameEmail(String fullname, String email) {
        if (fullname == null || fullname.isBlank()) {
            throw new IllegalArgumentException("Ho ten khong duoc trong");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email khong hop le");
        }
    }
}
