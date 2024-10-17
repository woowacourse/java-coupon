CREATE DATABASE IF NOT EXISTS coupon;

USE coupon;

CREATE TABLE IF NOT EXISTS coupon
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(255) NOT NULL,
    discount_money BIGINT       NOT NULL,
    discount_rate  BIGINT       NOT NULL,
    order_price    BIGINT       NOT NULL,
    category       VARCHAR(255) NOT NULL,
    start          DATETIME     NOT NULL,
    end            DATETIME     NOT NULL
);
