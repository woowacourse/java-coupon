CREATE DATABASE IF NOT EXISTS coupon;
USE coupon;

CREATE TABLE IF NOT EXISTS member
(
    id   BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS coupon
(
    id                   BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                 VARCHAR(255) NOT NULL,
    discount_amount      VARCHAR(255) NOT NULL,
    minimum_order_amount VARCHAR(255) NOT NULL,
    discount_rate        BIGINT       NOT NULL,
    category             VARCHAR(15)  NOT NULL,
    start_at             DATETIME     NOT NULL,
    end_at               DATETIME     NOT NULL
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS member_coupon
(
    id         BIGINT   NOT NULL AUTO_INCREMENT PRIMARY KEY,
    coupon_id  BIGINT   NOT NULL,
    member_id  BIGINT   NOT NULL,
    isUsed     BOOLEAN  NOT NULL,
    issued_at  DATETIME NOT NULL,
    expired_at DATETIME NOT NULL,

    FOREIGN KEY (coupon_id) REFERENCES coupon (id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE CASCADE
) ENGINE = InnoDB;
