CREATE DATABASE IF NOT EXISTS coupon;

USE coupon;

CREATE TABLE IF NOT EXISTS coupon (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    discount_amount INT NOT NULL,
    minimum_order_amount INT NOT NULL,
    category VARCHAR(50) NOT NULL,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS member_coupon (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    is_used BOOLEAN DEFAULT FALSE,
    issued_at DATETIME,
    expires_at DATETIME,
    FOREIGN KEY (coupon_id) REFERENCES coupons(id)
) engine=InnoDB;
