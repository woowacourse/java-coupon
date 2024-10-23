CREATE DATABASE coupon;
USE coupon;
CREATE TABLE member
(
    id          BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    created_at  DATETIME(6),
    modified_at DATETIME(6)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE TABLE coupon
(
    id           BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(30) NOT NULL,
    discount_amount     INT,
    minimum_order_price INT,
    category            VARCHAR(30),
    issue_started_at    DATETIME(6),
    issue_ended_at      DATETIME(6),
    created_at          DATETIME(6),
    modified_at         DATETIME(6),
    issue_count         BIGINT,
    issue_limit         BIGINT

) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE TABLE member_coupon
(
    id               BIGINT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id        BIGINT  NOT NULL,
    coupon_id        BIGINT  NOT NULL,
    is_used          BOOLEAN NOT NULL DEFAULT FALSE,
    issue_started_at DATETIME(6),
    issue_ended_at   DATETIME(6),
    created_at       DATETIME(6),
    modified_at      DATETIME(6),
    CONSTRAINT fk_member_coupon_member FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE CASCADE,
    CONSTRAINT fk_member_coupon_coupon FOREIGN KEY (coupon_id) REFERENCES coupon (id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
