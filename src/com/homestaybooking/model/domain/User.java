package com.homestaybooking.model.domain;

import com.homestaybooking.model.enums.UserRole;
import com.homestaybooking.model.enums.UserStatus;

import java.time.LocalDate;

public class User {
    public String id;
    public String fullname;
    public String email;
    public String password;
    public String phone;
    public LocalDate dob;
    public UserRole role;
    public UserStatus status;
    public long balance;

    public User() {
    }

    public User(String id, String fullname, String email, String password, String phone, LocalDate dob, UserRole role, UserStatus status, long balance) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.dob = dob;
        this.role = role;
        this.status = status;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return fullname + " - " + email + " (" + role + ")";
    }
}
