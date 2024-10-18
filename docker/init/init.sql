CREATE DATABASE IF NOT EXISTS coupon;
USE coupon;

CREATE TABLE IF NOT EXISTS coupon
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    name                VARCHAR(255) NOT NULL,
    discount_price      INT          NOT NULL,
    minimum_order_price INT          NOT NULL,
    discount_percent    DOUBLE       NOT NULL,
    category            VARCHAR(255) NOT NULL,
    issued_at           DATETIME     NOT NULL,
    expires_at          DATETIME     NOT NULL
);

CREATE TABLE IF NOT EXISTS member
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS member_coupon
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    member     BIGINT   NOT NULL,
    coupon     BIGINT   NOT NULL,
    is_used    BOOLEAN  NOT NULL,
    expires_at DATETIME NOT NULL,
    FOREIGN KEY (member) REFERENCES member (id),
    FOREIGN KEY (coupon) REFERENCES coupon (id)
);
