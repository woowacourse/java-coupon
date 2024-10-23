create database coupon;
use coupon;

CREATE TABLE `coupon`
(
    `id`                       bigint         NOT NULL AUTO_INCREMENT,
    `coupon_name`              varchar(255)   NOT NULL,
    `coupon_applicable_amount` decimal(38, 2) NOT NULL,
    `coupon_discount_amount`   decimal(38, 2) NOT NULL,
    `issuable_start_date`      date           NOT NULL,
    `issuable_end_date`        date           NOT NULL,
    `coupon_category`          enum('FASHION','HOME_APPLIANCES','FURNITURE','FOOD') NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `membercoupon`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `coupon_id`  bigint NOT NULL,
    `member_id`  bigint NOT NULL,
    `is_used`    bit(1) NOT NULL,
    `issued_at`  datetime(6) NOT NULL,
    `expires_at` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
