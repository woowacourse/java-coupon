CREATE DATABASE IF NOT EXISTS coupon;
USE coupon;

CREATE TABLE IF NOT EXISTS coupon
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    name                VARCHAR(30) NOT NULL,
    discount_amount     INT         NOT NULL,
    minimum_order_price INT         NOT NULL,
    category            VARCHAR(20) NOT NULL,
    issue_started_at    DATETIME(6) NOT NULL,
    issue_ended_at      DATETIME(6) NOT NULL,
    created_at          DATETIME(6) NOT NULL,
    updated_at          DATETIME(6) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS member
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    created_at DATETIME(6)  NOT NULL,
    updated_at DATETIME(6)  NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS member_coupon
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_id    BIGINT  NOT NULL,
    member_id    BIGINT  NOT NULL,
    used         BOOLEAN NOT NULL,
    issued_at    DATETIME(6),
    use_ended_at DATETIME(6),
    CONSTRAINT fk_member_coupon_coupon_id FOREIGN KEY (coupon_id) REFERENCES coupon (id),
    CONSTRAINT fk_member_coupon_member_id FOREIGN KEY (member_id) REFERENCES member (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
