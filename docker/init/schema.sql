CREATE DATABASE IF NOT EXISTS coupon;
USE coupon;

CREATE TABLE IF NOT EXISTS members
(
    member_id BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS coupons
(
    coupon_id        BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(30)  NOT NULL,
    discount_amount  BIGINT       NOT NULL,
    min_order_amount BIGINT       NOT NULL,
    category         VARCHAR(255) NOT NULL,
    issue_start_date DATETIME(6)  NOT NULL,
    issue_end_date   DATETIME(6)  NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS member_coupons
(
    member_coupon_id BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id        BIGINT      NOT NULL,
    coupon_id        BIGINT      NOT NULL,
    used             BOOLEAN     NOT NULL,
    issued_at        DATETIME(6) NOT NULL,
    expires_at       DATETIME(6) NOT NULL,
    CONSTRAINT fk_member_member_coupon FOREIGN KEY (member_id) REFERENCES members (member_id) ON DELETE CASCADE,
    CONSTRAINT fk_coupon_member_coupon FOREIGN KEY (coupon_id) REFERENCES coupons (coupon_id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
