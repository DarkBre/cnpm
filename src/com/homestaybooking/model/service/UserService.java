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
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Email hoặc mật khẩu không đúng"));
        if (!user.password.equals(password)) {
            throw new IllegalArgumentException("Email hoặc mật khẩu không đúng");
        }
        if (user.status == UserStatus.LOCKED) {
            throw new IllegalStateException("Tài khoản đang bị khóa");
        }
        currentUser = user;
        return user;
    }

    public User register(String fullname, String email, String password, String phone, LocalDate dob) {
        validateNameEmailPassword(fullname, email, password);
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }
        User user = new User(null, fullname.trim(), email.trim(), password, phone, dob, UserRole.CUSTOMER, UserStatus.ACTIVE, 0);
        currentUser = userRepository.save(user);
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }

    public User currentUser() {
        return currentUser;
    }

    public User requireLogin() {
        if (currentUser == null) {
            throw new IllegalStateException("Cần đăng nhập để thực hiện chức năng này");
        }
        return currentUser;
    }

    public User requireAdmin() {
        User user = requireLogin();
        if (user.role != UserRole.ADMIN) {
            throw new IllegalStateException("Chức năng này yêu cầu vai trò ADMIN");
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
                    throw new IllegalArgumentException("Email đã được người dùng khác sử dụng");
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
            throw new IllegalArgumentException("Mật khẩu cũ không đúng");
        }
        if (newPassword == null || newPassword.length() < 6) {
            throw new IllegalArgumentException("Mật khẩu mới tối thiểu 6 ký tự");
        }
        user.password = newPassword;
        userRepository.save(user);
    }

    public void lockUser(String userId) {
        requireAdmin();
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng"));
        user.status = UserStatus.LOCKED;
        userRepository.save(user);
    }

    public void unlockUser(String userId) {
        requireAdmin();
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng"));
        user.status = UserStatus.ACTIVE;
        userRepository.save(user);
    }

    public void promoteToOwner(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng"));
        if (user.role == UserRole.CUSTOMER) {
            user.role = UserRole.OWNER;
            userRepository.save(user);
        }
    }

    private void validateNameEmailPassword(String fullname, String email, String password) {
        validateNameEmail(fullname, email);
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Mật khẩu tối thiểu 6 ký tự");
        }
    }

    private void validateNameEmail(String fullname, String email) {
        if (fullname == null || fullname.isBlank()) {
            throw new IllegalArgumentException("Họ tên không được trống");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email không hợp lệ");
        }
    }
}
