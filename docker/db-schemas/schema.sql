CREATE DATABASE IF NOT EXISTS coupon;
USE coupon;

-- coupon 테이블 생성
CREATE TABLE IF NOT EXISTS coupon (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    discount_amount INT NOT NULL,
    discount_percent INT NOT NULL,
    category ENUM('FASHION', 'APPLIANCES', 'FURNITURE', 'FOOD') NOT NULL,
    minimum_order_price INT NOT NULL,
    issue_date_time DATETIME NOT NULL,
    expiration_date_time DATETIME NOT NULL
);

-- member_coupon 테이블 생성
CREATE TABLE IF NOT EXISTS member_coupon (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    is_used BOOLEAN NOT NULL,
    issued_date_time DATETIME NOT NULL,
    expiration_date_time DATETIME NOT NULL,
    FOREIGN KEY (coupon_id) REFERENCES coupon(id) ON DELETE CASCADE
);
