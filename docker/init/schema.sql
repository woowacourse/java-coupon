CREATE DATABASE IF NOT EXISTS `coupon`;
USE `coupon`;

CREATE TABLE Coupon (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_name VARCHAR(30) NOT NULL,
    discount_amount DECIMAL NOT NULL,
    minimum_order_amount DECIMAL NOT NULL,
    category ENUM('FASHION', 'HOME_APPLIANCES', 'FURNITURE', 'FOOD') NOT NULL,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL
);

CREATE TABLE Member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE MemberCoupon (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    is_used BOOLEAN NOT NULL,
    created_at DATETIME NOT NULL,
    expires_at DATETIME NOT NULL,
    CONSTRAINT fk_coupon FOREIGN KEY (coupon_id) REFERENCES Coupon(id),
    CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES Member(id)
);
