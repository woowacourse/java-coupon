create database if not exists coupon;
use coupon;

create table if not exists coupon
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    name                VARCHAR(255) NOT NULL,
    discount_money      BIGINT       NOT NULL,
    minimum_order_money BIGINT       NOT NULL,
    coupon_category     VARCHAR(50)  NOT NULL,
    since_date          DATE         NOT NULL,
    until_date          DATE         NOT NULL
) engine = InnoDB;

create table if not exists member
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
) engine = InnoDB;

create table if not exists member_coupon
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_id  BIGINT  NOT NULL,
    member_id  BIGINT  NOT NULL,
    is_used    BOOLEAN NOT NULL,
    issue_date DATE    NOT NULL,
    CONSTRAINT fk_coupon FOREIGN KEY (coupon_id) REFERENCES coupon (id) ON DELETE CASCADE
) engine = InnoDB;
