create database if not exists coupon;
use coupon;

CREATE TABLE member (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE coupon (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        coupon_name VARCHAR(255) NOT NULL,
                        category ENUM('FASHION', 'ELECTRONICS', 'FURNITURE', 'FOOD') NOT NULL,
                        start_date DATETIME(6) NOT NULL,
                        end_date DATETIME(6) NOT NULL,
                        order_amount INTEGER NOT NULL,
                        discount_amount INTEGER NOT NULL
);

CREATE TABLE membercoupon (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              coupon_id BIGINT NOT NULL,
                              member_id BIGINT NOT NULL,
                              is_used BOOLEAN NOT NULL,
                              created_at DATETIME(6) NOT NULL,
                              expired_at DATETIME(6) NOT NULL
);
