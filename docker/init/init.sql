CREATE DATABASE IF NOT EXISTS coupon;

USE coupon;

CREATE TABLE IF NOT EXISTS coupon
(
    id                     BIGINT AUTO_INCREMENT PRIMARY KEY,
    name                   VARCHAR(255)                                             NOT NULL,
    discount_amount        INT                                                      NOT NULL,
    min_order_amount       INT                                                      NOT NULL,
    issued_start_date_time DATETIME                                                 NOT NULL,
    issued_end_date_time   DATETIME                                                 NOT NULL,
    category               ENUM ('FOOD', 'FURNITURE', 'HOME_APPLIANCES', 'FASHION') NOT NULL,
    created_at             DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at             DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS member_coupon
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_id  BIGINT   NOT NULL,
    member_id  BIGINT   NOT NULL,
    is_used    BOOLEAN  NOT NULL,
    issued_at  DATETIME NOT NULL,
    expires_at DATETIME NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (coupon_id) REFERENCES coupon (id)
);
