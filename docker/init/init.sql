CREATE DATABASE IF NOT EXISTS coupon;
USE coupon;

CREATE TABLE IF NOT EXISTS coupon (
    id                   BIGINT NOT NULL AUTO_INCREMENT,
    name                 VARCHAR(255) NOT NULL,
    discount_amount      INT NOT NULL,
    min_order_amount     INT NOT NULL,
    category             ENUM('FASHION', 'HOME_APPLIANCES', 'FURNITURE', 'FOODS') NOT NULL,
    issuance_start_date  DATETIME(6) NOT NULL,
    issuance_end_date    DATETIME(6) NOT NULL,
    CONSTRAINT pk_coupon PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS member (
    id                   BIGINT NOT NULL AUTO_INCREMENT,
    name                 VARCHAR(255) NOT NULL,
    CONSTRAINT pk_member PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS published_coupon (
    id                   BIGINT NOT NULL AUTO_INCREMENT,
    member_id            BIGINT NOT NULL,
    coupon_id            BIGINT NOT NULL,
    is_used              BOOLEAN NOT NULL,
    issuance_date        DATETIME(6) NOT NULL,
    expiration_date      DATETIME(6) NOT NULL,
    CONSTRAINT pk_published_coupon PRIMARY KEY (id)
    );
