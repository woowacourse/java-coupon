CREATE DATABASE IF NOT EXISTS coupon;
USE coupon;

CREATE TABLE IF NOT EXISTS coupon (
    id                   BIGINT NOT NULL AUTO_INCREMENT,
    name                 VARCHAR(255) NOT NULL,
    category             ENUM('FASHION', 'ELECTRONICS', 'FURNITURE', 'GROCERIES') NOT NULL,
    discount_amount      DECIMAL(38, 2) NOT NULL,
    minimum_order_amount DECIMAL(38, 2) NOT NULL,
    start_date           DATETIME(6) NOT NULL,
    end_date             DATETIME(6) NOT NULL,
    CONSTRAINT pk_coupon PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS member (
    id       BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    CONSTRAINT pk_member PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS member_coupon (
    id          BIGINT NOT NULL AUTO_INCREMENT,
    member_id   BIGINT NOT NULL,
    coupon_id   BIGINT NOT NULL,
    is_active   BOOLEAN NOT NULL,
    issue_date  DATETIME(6) NOT NULL,
    expire_date DATETIME(6) NOT NULL,
    CONSTRAINT pk_member_coupon PRIMARY KEY (id),
    CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES member(id),
    CONSTRAINT fk_coupon FOREIGN KEY (coupon_id) REFERENCES coupon(id)
    );
