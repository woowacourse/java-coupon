CREATE DATABASE IF NOT EXISTS coupon;

USE coupon;

CREATE TABLE IF NOT EXISTS coupon
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_name      varchar(30)  NOT NULL,
    discount_price   INT          NOT NULL,
    category         varchar(256) NOT NULL,
    sale_order_price INT          NOT NULL,
    start_at         DATETIME     NOT NULL,
    end_at           DATETIME     NOT NULL
);

CREATE TABLE IF NOT EXISTS member_coupon
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id        BIGINT,
    coupon_id        BIGINT,
    used             TINYINT(1)    NOT NULL,
    issued_at        DATETIME     NOT NULL,
    expired_at       DATEITME     NOT NULL
)
