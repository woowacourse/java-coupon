create database if not exists coupon;
use coupon;

create table if not exists member
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    nickname VARCHAR(10) NOT NULL
);

create table if not exists coupon
(
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name                 VARCHAR(30)                                              NOT NULL,
    discount_amount      DECIMAL(10, 2)                                           NOT NULL,
    minimum_order_amount DECIMAL(10, 2)                                           NOT NULL,
    category             ENUM ('FASHION', 'HOME_APPLIANCES', 'FURNITURE', 'FOOD') NOT NULL,
    start_at             DATETIME(6)                                              NOT NULL,
    end_at               DATETIME(6)                                              NOT NULL
);

create table if not exists member_coupon
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_id  BIGINT    NOT NULL,
    member_id  BIGINT    NOT NULL,
    used       BOOLEAN   NOT NULL,
    issued_at  DATETIME(6) NOT NULL,
    expired_at DATETIME(6) NOT NULL
);
