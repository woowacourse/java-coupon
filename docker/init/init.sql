CREATE DATABASE IF NOT EXISTS coupon;
USE coupon;

CREATE TABLE IF NOT EXISTS coupon (
    id                   BIGINT NOT NULL AUTO_INCREMENT,
    name                 VARCHAR(255),
    category             ENUM('FASHION', 'ELECTRONICS', 'FURNITURE', 'GROCERIES') NOT NULL,
    discount_amount      DECIMAL(38, 2),
    minimum_order_amount DECIMAL(38, 2),
    start_date           DATETIME(6),
    end_date             DATETIME(6),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS member (
    id       BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255),
    password VARCHAR(255),
    email    VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS member_coupon (
    id          BIGINT NOT NULL AUTO_INCREMENT,
    member_id   BIGINT,
    coupon_id   BIGINT,
    is_active   BOOLEAN,
    issue_date  DATETIME(6),
    expire_date DATETIME(6),
    PRIMARY KEY (id)
);
