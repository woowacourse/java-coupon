CREATE DATABASE IF NOT EXISTS coupon;

USE coupon;

CREATE TABLE IF NOT EXISTS coupon
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_name      varchar(30)  NOT NULL,
    discount_price   INT          NOT NULL,
    category         varchar(256) NOT NULL,
    sale_order_price INT          NOT NULL,
    start_at         datetime     NOT NULL,
    end_at           datetime     NOT NULL
);
