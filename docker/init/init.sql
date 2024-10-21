CREATE DATABASE IF NOT EXISTS coupon;

USE coupon;

CREATE TABLE IF NOT EXISTS coupon (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    discount_amount INT NOT NULL,
    minimum_order_amount INT NOT NULL,
    category ENUM('FASHION', 'ELECTRONICS', 'FURNITURE', 'FOOD') NOT NULL,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    created_at DATETIME NOT NULL,
    modified_at DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    created_at DATETIME NOT NULL,
    modified_at DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS member_coupon (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    used BOOLEAN NOT NULL,
    issued_date DATETIME NOT NULL,
    expiry_date DATETIME NOT NULL,
    created_at DATETIME NOT NULL,
    modified_at DATETIME NOT NULL,
    FOREIGN KEY (coupon_id) REFERENCES coupon(id),
    FOREIGN KEY (member_id) REFERENCES member(id)
);
