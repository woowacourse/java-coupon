CREATE DATABASE IF NOT EXISTS `coupon`;
USE `coupon`;

CREATE TABLE IF NOT EXISTS `Coupon` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(30) NOT NULL,
    `discountAmount` INT NOT NULL,
    `minOrderAmount` INT NOT NULL,
    `category` TINYINT NOT NULL,
    `startDate` DATE NOT NULL,
    `endDate` DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS `MemberCoupon` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `couponId` BIGINT NOT NULL,
    `memberId` BIGINT NOT NULL,
    `grantedAt` TIMESTAMP NOT NULL,
    `expireAt` TIMESTAMP NOT NULL,
    `isUsed` BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS `Member` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `account` VARCHAR(10) NOT NULL,
    `password` VARCHAR(30) NOT NULL
);
