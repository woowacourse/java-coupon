CREATE DATABASE IF NOT EXISTS coupon;

USE coupon;

CREATE TABLE IF NOT EXISTS coupon
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_name      varchar(30)  NOT NULL,
    sale_price       INT          NOT NULL,
    sale_ratio       DOUBLE       NOT NULL,
    category         varchar(256) NOT NULL,
    sale_order_price INT          NOT NULL,
    start_time       datetime     NOT NULL,
    end_time         datetime     NOT NULL
);
