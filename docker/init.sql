CREATE TABLE member
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE coupon
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(30) NOT NULL,
    discount_amount  INT         NOT NULL,
    min_order_amount INT         NOT NULL,
    category         ENUM('FASHION', 'ELECTRONICS', 'FURNITURE', 'FOOD') NOT NULL,
    start_date       DATETIME    NOT NULL,
    end_date         DATETIME    NOT NULL
);

CREATE TABLE member_coupon
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_id  BIGINT   NOT NULL,
    member_id  BIGINT   NOT NULL,
    is_used    BOOLEAN,
    issued_at  DATETIME NOT NULL,
    expires_at DATETIME NOT NULL,
    FOREIGN KEY (coupon_id) REFERENCES Coupon (id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES Member (id) ON DELETE CASCADE
);
