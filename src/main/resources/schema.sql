CREATE TABLE Coupon (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    discountAmount INTEGER NOT NULL,
    minOrderAmount INTEGER NOT NULL,
    category VARCHAR(255) NOT NULL,
    startDate TIMESTAMP NOT NULL,
    endDate TIMESTAMP NOT NULL
);

CREATE TABLE Member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL
);

CREATE TABLE MemberCoupon (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    couponId BIGINT NOT NULL,
    memberId BIGINT NOT NULL,
    isUsed BOOLEAN NOT NULL,
    issuedAt TIMESTAMP NOT NULL,
    expiredAt TIMESTAMP NOT NULL
);
