CREATE DATABASE IF NOT EXISTS `coupon`;

USE `coupon`;

CREATE TABLE IF NOT EXISTS `coupon` (
    `id`                BIGINT NOT NULL AUTO_INCREMENT,
    `name`              VARCHAR(30) NOT NULL,
    `discount_amount`   INT NOT NULL,
    `min_order_amount`  INT NOT NULL,
    `category`          ENUM ('fashion','home_appliances','furniture','food') NOT NULL,
    `issuance_start_at` DATETIME NOT NULL,
    `issuance_end_at`   DATETIME NOT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS `member_coupon` (
    `id`         BIGINT NOT NULL AUTO_INCREMENT,
    `coupon_id`  BIGINT NOT NULL,
    `member_id`  BIGINT NOT NULL,
    `used`       BOOLEAN NOT NULL,
    `issued_at`  TIMESTAMP(6) NOT NULL,
    `expires_at` TIMESTAMP(6),
    PRIMARY KEY (`id`)
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4;
