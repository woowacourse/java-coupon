create database if not exists coupon;
use coupon;

create table if not exists coupon
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    discount      INT          NOT NULL,
    minimum_order INT          NOT NULL,
    category      VARCHAR(255) NOT NULL,
    start         DATE         NOT NULL,
    end           DATE         NOT NULL
);

create table if not exists member_coupon
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_id BIGINT  NOT NULL,
    member_id BIGINT  NOT NULL,
    used      BOOLEAN NOT NULL,
    start     DATE    NOT NULL,
    end       DATE    NOT NULL
);
