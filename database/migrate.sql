DROP DATABASE IF EXISTS homestaybooking;
CREATE DATABASE homestaybooking CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE homestaybooking;

CREATE TABLE users (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    fullname VARCHAR(100) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    dob DATE,
    role ENUM('ADMIN', 'CUSTOMER', 'OWNER') NOT NULL DEFAULT 'CUSTOMER',
    status ENUM('ACTIVE', 'LOCKED') NOT NULL DEFAULT 'ACTIVE',
    balance BIGINT UNSIGNED NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    locked_at DATETIME NULL,
    unlocked_at DATETIME NULL,
    CONSTRAINT chk_users_balance_non_negative CHECK (balance >= 0)
);

CREATE TABLE homestays (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    owner_id CHAR(36) NOT NULL,
    name VARCHAR(150) NOT NULL,
    address VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL,
    description TEXT,
    image_url VARCHAR(500),
    status ENUM('ACTIVE', 'INACTIVE', 'DELETED') NOT NULL DEFAULT 'ACTIVE',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME NULL,
    CONSTRAINT fk_homestays_owner FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE TABLE rooms (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    homestay_id CHAR(36) NOT NULL,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    capacity INT NOT NULL,
    description TEXT,
    image_url VARCHAR(500),
    status ENUM('AVAILABLE', 'INACTIVE', 'MAINTENANCE') NOT NULL DEFAULT 'AVAILABLE',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME NULL,
    CONSTRAINT fk_rooms_homestay FOREIGN KEY (homestay_id) REFERENCES homestays(id),
    CONSTRAINT chk_rooms_capacity_positive CHECK (capacity > 0)
);

CREATE TABLE amenities (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME NULL
);

CREATE TABLE room_amenities (
    room_id CHAR(36) NOT NULL,
    amenity_id CHAR(36) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (room_id, amenity_id),
    CONSTRAINT fk_room_amenities_room FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE,
    CONSTRAINT fk_room_amenities_amenity FOREIGN KEY (amenity_id) REFERENCES amenities(id) ON DELETE CASCADE
);

CREATE TABLE pricing_schedules (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    room_id CHAR(36) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    price_per_night BIGINT UNSIGNED NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME NULL,
    CONSTRAINT fk_pricing_schedules_room FOREIGN KEY (room_id) REFERENCES rooms(id),
    CONSTRAINT chk_pricing_schedules_date_range CHECK (start_date <= end_date),
    CONSTRAINT chk_pricing_schedules_price_positive CHECK (price_per_night > 0)
);

CREATE TABLE blocked_dates (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    room_id CHAR(36) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    reason VARCHAR(255),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME NULL,
    CONSTRAINT fk_blocked_dates_room FOREIGN KEY (room_id) REFERENCES rooms(id),
    CONSTRAINT chk_blocked_dates_date_range CHECK (start_date < end_date)
);

CREATE TABLE vouchers (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    created_by CHAR(36) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(150) NOT NULL,
    discount_type ENUM('FIXED_AMOUNT', 'PERCENTAGE') NOT NULL,
    discount_value BIGINT UNSIGNED NOT NULL,
    max_discount_amount BIGINT UNSIGNED NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    max_usage INT NOT NULL,
    used_count INT NOT NULL DEFAULT 0,
    min_order_value BIGINT UNSIGNED NOT NULL DEFAULT 0,
    status ENUM('ACTIVE', 'INACTIVE', 'EXPIRED') NOT NULL DEFAULT 'ACTIVE',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME NULL,
    CONSTRAINT fk_vouchers_created_by FOREIGN KEY (created_by) REFERENCES users(id),
    CONSTRAINT chk_vouchers_usage CHECK (used_count >= 0 AND max_usage >= 0 AND used_count <= max_usage),
    CONSTRAINT chk_vouchers_date_range CHECK (start_date <= end_date),
    CONSTRAINT chk_vouchers_discount_value CHECK (
        (discount_type = 'FIXED_AMOUNT' AND discount_value > 0)
        OR (discount_type = 'PERCENTAGE' AND discount_value BETWEEN 1 AND 100)
    )
);

CREATE TABLE bookings (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    booked_by CHAR(36) NOT NULL,
    room_id CHAR(36) NOT NULL,
    voucher_id CHAR(36) NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    total_nights INT NOT NULL,
    original_amount BIGINT UNSIGNED NOT NULL,
    discount_amount BIGINT UNSIGNED NOT NULL DEFAULT 0,
    total_amount BIGINT UNSIGNED NOT NULL,
    cancellation_fee BIGINT UNSIGNED NOT NULL DEFAULT 0,
    refund_amount BIGINT UNSIGNED NOT NULL DEFAULT 0,
    cancelled_at DATETIME NULL,
    status ENUM('PENDING_PAYMENT', 'CONFIRMED', 'CANCELLED', 'COMPLETED') NOT NULL DEFAULT 'PENDING_PAYMENT',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME NULL,
    CONSTRAINT fk_bookings_user FOREIGN KEY (booked_by) REFERENCES users(id),
    CONSTRAINT fk_bookings_room FOREIGN KEY (room_id) REFERENCES rooms(id),
    CONSTRAINT fk_bookings_voucher FOREIGN KEY (voucher_id) REFERENCES vouchers(id),
    CONSTRAINT chk_bookings_date_range CHECK (check_in_date < check_out_date),
    CONSTRAINT chk_bookings_total_nights CHECK (total_nights > 0),
    CONSTRAINT chk_bookings_amount CHECK (total_amount = original_amount - discount_amount)
);

CREATE TABLE invoices (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    booking_id CHAR(36) NOT NULL UNIQUE,
    subtotal BIGINT UNSIGNED NOT NULL,
    discount_amount BIGINT UNSIGNED NOT NULL DEFAULT 0,
    total_amount BIGINT UNSIGNED NOT NULL,
    payment_method ENUM('WALLET') NOT NULL DEFAULT 'WALLET',
    payment_status ENUM('UNPAID', 'PAID', 'REFUNDED', 'FAILED') NOT NULL DEFAULT 'UNPAID',
    payment_date DATETIME NULL,
    settlement_status ENUM('NOT_SETTLED', 'SETTLED', 'CANCELLED') NOT NULL DEFAULT 'NOT_SETTLED',
    settled_at DATETIME NULL,
    refunded_at DATETIME NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME NULL,
    CONSTRAINT fk_invoices_booking FOREIGN KEY (booking_id) REFERENCES bookings(id),
    CONSTRAINT chk_invoices_amount CHECK (total_amount = subtotal - discount_amount)
);

CREATE TABLE wallet_transactions (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    user_id CHAR(36) NOT NULL,
    booking_id CHAR(36) NULL,
    transaction_type ENUM('DEPOSIT', 'PAYMENT', 'REFUND', 'OWNER_REVENUE', 'WITHDRAW') NOT NULL,
    amount BIGINT NOT NULL,
    balance_before BIGINT UNSIGNED NOT NULL,
    balance_after BIGINT UNSIGNED NOT NULL,
    description VARCHAR(255),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_wallet_transactions_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_wallet_transactions_booking FOREIGN KEY (booking_id) REFERENCES bookings(id)
);

CREATE TABLE reviews (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    booking_id CHAR(36) NOT NULL UNIQUE,
    rating TINYINT UNSIGNED NOT NULL,
    comment TEXT,
    review_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME NULL,
    CONSTRAINT fk_reviews_booking FOREIGN KEY (booking_id) REFERENCES bookings(id),
    CONSTRAINT chk_reviews_rating CHECK (rating BETWEEN 1 AND 5)
);

CREATE INDEX idx_homestays_owner_id ON homestays(owner_id);
CREATE INDEX idx_rooms_homestay_id ON rooms(homestay_id);
CREATE INDEX idx_pricing_schedules_room_dates ON pricing_schedules(room_id, start_date, end_date);
CREATE INDEX idx_blocked_dates_room_dates ON blocked_dates(room_id, start_date, end_date);
CREATE INDEX idx_bookings_room_dates ON bookings(room_id, check_in_date, check_out_date);
CREATE INDEX idx_bookings_booked_by ON bookings(booked_by);
CREATE INDEX idx_vouchers_created_by ON vouchers(created_by);
CREATE INDEX idx_wallet_transactions_user_id ON wallet_transactions(user_id);
