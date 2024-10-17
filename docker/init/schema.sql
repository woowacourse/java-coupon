CREATE DATABASE IF NOT EXISTS coupon;
USE coupon;

CREATE TABLE IF NOT EXISTS coupon
(
    coupon_id               BIGINT          NOT NULL AUTO_INCREMENT,
    name                    VARCHAR(30)     NOT NULL,
    discount_amount         INT,
    minimum_order_amount    INT,
    category                ENUM('FASHION', 'ELECTRONICS', 'FURNITURE', 'FOOD') NOT NULL,
    issue_started_at        DATETIME        NOT NULL,
    issue_ended_at          DATETIME        NOT NULL,
    issue_limit             BIGINT          NOT NULL,
    issue_count             BIGINT          NOT NULL,
    PRIMARY KEY (coupon_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS member
(
    member_id   BIGINT          NOT NULL AUTO_INCREMENT,
    name        VARCHAR(20)     NOT NULL,
    PRIMARY KEY (member_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS member_coupon
(
    member_coupon_id    BIGINT  NOT NULL AUTO_INCREMENT,
    coupon_id           BIGINT,
    member_id           BIGINT,
    used                BOOLEAN,
    issued_at           DATETIME,
    use_ended_at        DATETIME,
    PRIMARY KEY (member_coupon_id),
    CONSTRAINT fk_coupon FOREIGN KEY (coupon_id) REFERENCES coupon(coupon_id),
    CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES member(member_id)
) ENGINE=InnoDB;
