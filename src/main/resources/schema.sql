create database coupon if not exists coupon;
use coupon;

create table if not exists coupon
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    name                VARCHAR(30)    NOT NULL,
    discount_amount     DECIMAL(10, 2) NOT NULL,
    minimum_order_price DECIMAL(10, 2) NOT NULL,
    coupon_category     VARCHAR(50)    NOT NULL,
    issue_started_at    TIMESTAMP      NOT NULL,
    issue_ended_at      TIMESTAMP      NOT NULL
) engine = InnoDB;

create table if not exists member_coupon
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_id BIGINT    NOT NULL,
    member_id BIGINT    NOT NULL,
    used      BOOLEAN   NOT NULL,
    issued_at TIMESTAMP NOT NULL,
    expire_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_coupon FOREIGN KEY (coupon_id) REFERENCES coupon (id) ON DELETE CASCADE
) engine = InnoDB;

