package com.homestaybooking.model.repository;

import com.homestaybooking.connection.Database;
import com.homestaybooking.model.domain.User;
import com.homestaybooking.model.enums.UserRole;
import com.homestaybooking.model.enums.UserStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private static final List<User> USERS = new ArrayList<>();

    static {
        USERS.add(new User("u-admin", "Quản trị hệ thống", "admin@homestay.local", "123456", "0901000001", LocalDate.of(1990, 1, 15), UserRole.ADMIN, UserStatus.ACTIVE, 0));
        USERS.add(new User("u-owner", "Nguyễn Minh Chủ nhà", "owner@homestay.local", "123456", "0902000002", LocalDate.of(1987, 5, 20), UserRole.OWNER, UserStatus.ACTIVE, 3200000));
        USERS.add(new User("u-customer", "Lê An Khách hàng", "customer@homestay.local", "123456", "0904000004", LocalDate.of(1998, 4, 12), UserRole.CUSTOMER, UserStatus.ACTIVE, 3020000));
    }

    public List<User> findAll(String keyword) {
        String key = safe(keyword).toLowerCase();
        return USERS.stream()
                .filter(user -> key.isBlank()
                        || safe(user.fullname).toLowerCase().contains(key)
                        || safe(user.email).toLowerCase().contains(key)
                        || safe(user.phone).toLowerCase().contains(key))
                .toList();
    }

    public Optional<User> findById(String id) {
        return USERS.stream().filter(user -> user.id.equals(id)).findFirst();
    }

    public Optional<User> findByEmail(String email) {
        return USERS.stream().filter(user -> user.email.equalsIgnoreCase(safe(email))).findFirst();
    }

    public User save(User user) {
        if (user.id == null || user.id.isBlank()) {
            user.id = Database.newId();
            USERS.add(user);
            return user;
        }
        for (int i = 0; i < USERS.size(); i++) {
            if (USERS.get(i).id.equals(user.id)) {
                USERS.set(i, user);
                return user;
            }
        }
        USERS.add(user);
        return user;
    }

    public void updateBalance(String userId, long balance) {
        User user = findById(userId).orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng"));
        user.balance = Math.max(0, balance);
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
