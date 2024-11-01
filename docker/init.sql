create DATABASE IF NOT EXISTS coupon;

use coupon;

CREATE TABLE If NOT EXISTS coupon
(
    id                   BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                 VARCHAR(30) NOT NULL,
    minimum_order_amount INT         NOT NULL,
    discount_amount      INT         NOT NULL,
    discount_rate        INT         NOT NULL,
    category             VARCHAR(30),
    issue_start_date     DATETIME,
    issue_end_date       DATETIME
);

CREATE TABLE IF NOT EXISTS member
(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS member_coupon (
    id         BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    coupon_id   BIGINT      NOT NULL,
    member_id   BIGINT      NOT NULL,
    is_used     BOOLEAN     NOT NULL DEFAULT FALSE,
    issue_at    DATE        NOT NULL,
    expired_at  DATE,
);
