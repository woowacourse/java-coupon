CREATE DATABASE IF NOT EXISTS coupon;
USE coupon;
CREATE TABLE IF NOT EXISTS coupon
(
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    name                VARCHAR(255) NOT NULL,
    category            VARCHAR(255) NOT NULL,
    discount_price      BIGINT       NOT NULL,
    minimum_order_price BIGINT       NOT NULL,
    issuable_start_date DATE         NOT NULL,
    issuable_end_date   DATE         NOT NULL
);

CREATE TABLE IF NOT EXISTS issued_coupon
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    coupon_id  BIGINT    NOT NULL,
    used       BOOLEAN   NOT NULL,
    member_id  BIGINT    NOT NULL,
    issued_at  TIMESTAMP NOT NULL,
    expired_at TIMESTAMP NOT NULL,
    FOREIGN KEY (coupon_id) REFERENCES coupon (id)
);

CREATE TABLE IF NOT EXISTS member
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);
