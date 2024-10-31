CREATE TABLE Coupon
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    discount_amount  INT          NOT NULL,
    min_order_amount INT          NOT NULL,
    category         VARCHAR(20) NOT NULL,
    start_date       DATE         NOT NULL,
    end_date         DATE         NOT NULL
);

CREATE TABLE Member
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE MemberCoupon
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_id       BIGINT      NOT NULL,
    member_id       BIGINT      NOT NULL,
    usage_status    VARCHAR(50) NOT NULL,
    issuance_date   DATE        NOT NULL,
    expiration_date DATE        NOT NULL
);
