CREATE DATABASE IF NOT EXISTS `coupon`;
USE `coupon`;

CREATE TABLE IF NOT EXISTS `Coupon` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(30) NOT NULL,
    `discount` INT NOT NULL,
    `minAmount` INT NOT NULL,
    `startDate` DATE NOT NULL,
    `endDate` DATE NOT NULL,
    `category` TINYINT NOT NULL
);

CREATE TABLE IF NOT EXISTS `MemberCoupon` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `couponId` BIGINT NOT NULL,
    `memberId` BIGINT NOT NULL,
    `used` BOOLEAN NOT NULL,
    `issuedAt` TIMESTAMP NOT NULL
);
