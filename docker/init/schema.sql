CREATE DATABASE IF NOT EXISTS coupon;
USE coupon;

CREATE TABLE IF NOT EXISTS coupon
(
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(30) NOT NULL,
    discount_amount      INTEGER     NOT NULL,
    minimum_order_amount INTEGER     NOT NULL,
    category             VARCHAR(50) NOT NULL,
    start_date           DATETIME    NOT NULL,
    end_date             DATETIME    NOT NULL
);
