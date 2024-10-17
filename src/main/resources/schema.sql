CREATE DATABASE coupon;
USE coupon;

CREATE TABLE Coupon
(
    coupon_id        BIGINT       NOT NULL AUTO_INCREMENT,
    name             VARCHAR(30)  NOT NULL,
    discount_amount  INT,
    min_order_amount INT,
    category         VARCHAR(255),
    start_date       DATETIME     NOT NULL,
    end_date         DATETIME     NOT NULL,
    PRIMARY KEY (coupon_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE Member
(
    member_id BIGINT AUTO_INCREMENT,
    name      VARCHAR(20) NOT NULL,
    PRIMARY KEY (member_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE MemberCoupon
(
    member_coupon_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_id        BIGINT,
    member_id        BIGINT,
    is_used          BOOLEAN DEFAULT FALSE,
    issued_at        DATETIME,
    expires_at       DATETIME,
    CONSTRAINT FK_MemberCoupon_Coupon FOREIGN KEY (coupon_id) REFERENCES Coupon (coupon_id),
    CONSTRAINT FK_MemberCoupon_Member FOREIGN KEY (member_id) REFERENCES Member (member_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
