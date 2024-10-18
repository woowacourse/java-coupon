DROP DATABASE IF EXISTS coupon;
CREATE DATABASE coupon;
USE coupon;

CREATE TABLE coupon
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    discount INTEGER,
    discount_rate INTEGER,
    min_order_price INTEGER,
    category VARCHAR(255),
    issuable_from TIMESTAMP(6),
    issuable_to TIMESTAMP(6)
);

CREATE TABLE member
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE member_coupon
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_id BIGINT,
    member_id BIGINT,
    used BOOLEAN,
    issued_at TIMESTAMP(6),
    expired_at TIMESTAMP(6)
);
