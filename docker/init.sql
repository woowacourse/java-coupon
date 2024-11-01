CREATE DATABASE IF NOT EXISTS coupon;
USE coupon;

CREATE TABLE coupon
(
    discountAmount     INT                                                  NOT NULL,
    minimumOrderAmount INT                                                  NOT NULL,
    id                 BIGINT                                               NOT NULL AUTO_INCREMENT,
    validFrom          DATETIME(6)                                          NOT NULL,
    validTo            DATETIME(6)                                          NOT NULL,
    name               VARCHAR(255)                                         NOT NULL,
    category           ENUM ('FASHION', 'ELECTRONICS', 'FURNITURE', 'FOOD') NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE member_coupon
(
    expiredAt  DATE,
    issuedAt   DATE,
    used       BIT    NOT NULL,
    couponId   BIGINT NOT NULL,
    id         BIGINT NOT NULL AUTO_INCREMENT,
    members_id BIGINT,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE members
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    email    VARCHAR(255) NOT NULL,
    name     VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

ALTER TABLE member_coupon
ADD CONSTRAINT fk_member_coupon_members
FOREIGN KEY (members_id)
REFERENCES members (id);
