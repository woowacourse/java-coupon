CREATE TABLE coupon
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(30) NOT NULL,
    product_category ENUM('패션', '가전', '가구', '식품'),
    discount_amount  INT         NOT NULL,
    min_order_amount INT         NOT NULL,
    issue_started_at DATETIME    NOT NULL,
    issue_ended_at   DATETIME    NOT NULL
);

CREATE TABLE member
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
);

CREATE TABLE member_coupon
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT  NOT NULL,
    coupon_id BIGINT  NOT NULL,
    is_used   BOOLEAN NOT NULL,
    issued_at DATETIME,
    ended_at  DATETIME,
    FOREIGN KEY (member_id) REFERENCES member (id),
    FOREIGN KEY (coupon_id) REFERENCES coupon (id)
);
