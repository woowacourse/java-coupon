CREATE DATABASE coupon;
USE coupon;

CREATE TABLE IF NOT EXISTS member
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS coupon
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    discount_amount INT,
    discount_rate   INT,
    order_price     INT          NOT NULL,
    category VARCHAR(255)  NOT NULL,
    issue_start_at  DATETIME(6) NOT NULL,
    issue_end_at    DATETIME(6) NOT NULL,
    CHECK (category IN ('FASHION', 'HOME_APPLIANCE', 'FURNITURE', 'FOOD'))
);

CREATE TABLE IF NOT EXISTS member_coupon
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id  BIGINT,
    coupon_id  BIGINT,
    used       BOOLEAN NOT NULL,
    issue_at   DATETIME(6) NOT NULL,
    expired_at DATETIME(6) NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member (id),
    FOREIGN KEY (coupon_id) REFERENCES coupon (id)
);
