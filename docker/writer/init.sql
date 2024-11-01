CREATE TABLE `member` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `created_at` datetime(6) NOT NULL,
                          `modified_at` datetime(6) NOT NULL,
                          `name` varchar(255) NOT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `coupon` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `created_at` datetime(6) NOT NULL,
                          `modified_at` datetime(6) NOT NULL,
                          `discount_amount` bigint DEFAULT NULL,
                          `discount_rate` int DEFAULT NULL,
                          `end_date` date DEFAULT NULL,
                          `start_date` date DEFAULT NULL,
                          `minimum_order_amount` bigint DEFAULT NULL,
                          `name` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `member_coupon` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `created_at` datetime(6) NOT NULL,
                                 `modified_at` datetime(6) NOT NULL,
                                 `coupon_id` bigint NOT NULL,
                                 `date` date DEFAULT NULL,
                                 `member_id` bigint NOT NULL,
                                 `used` bit(1) NOT NULL,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
