CREATE DATABASE IF NOT EXISTS `coupon`;
USE `coupon`;

CREATE TABLE IF NOT EXISTS Coupon (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        name VARCHAR(255),
                        discountAmount INT,
                        minOrderAmount INT,
                        startDate DATE,
                        endDate DATE,
                        category ENUM('FASHION', 'ELECTRONICS', 'FURNITURE', 'FOOD'),
                        PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS MemberCoupon (
                              id BIGINT NOT NULL AUTO_INCREMENT,
                              expirationDate DATE,
                              issueDate DATE,
                              used BIT,
                              couponId BIGINT,
                              memberId BIGINT,
                              PRIMARY KEY (id),
                              CONSTRAINT fk_coupon FOREIGN KEY (couponId) REFERENCES Coupon (id),
                              CONSTRAINT fk_member FOREIGN KEY (memberId) REFERENCES Member (id)
);
