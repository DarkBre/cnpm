package com.homestaybooking.controller;

import com.homestaybooking.model.domain.User;
import com.homestaybooking.model.service.UserService;

import java.time.LocalDate;
import java.util.List;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public User login(String email, String password) {
        return userService.login(email, password);
    }

    public User register(String fullname, String email, String password, String phone, LocalDate dob) {
        return userService.register(fullname, email, password, phone, dob);
    }

    public void logout() {
        userService.logout();
    }

    public User currentUser() {
        return userService.currentUser();
    }

    public User updateProfile(String fullname, String email, String phone, LocalDate dob) {
        return userService.updateProfile(fullname, email, phone, dob);
    }

    public List<User> listUsers(String keyword) {
        return userService.listUsers(keyword);
    }

    public void lockUser(String userId) {
        userService.lockUser(userId);
    }

    public void unlockUser(String userId) {
        userService.unlockUser(userId);
    }
}
