CREATE TABLE IF NOT EXISTS coupon_entity
(
    id               bigint  NOT NULL AUTO_INCREMENT,
    discount_range   integer NOT NULL,
    issue_end_date   date,
    issue_start_date date,
    discount_amount  bigint  NOT NULL,
    min_order_amount bigint  NOT NULL,
    name             varchar(255),
    category         ENUM ('FASHION', 'ELECTRONICS', 'FURNITURE', 'FOOD'),
    PRIMARY KEY (id)
    ) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS user_entity
(
    id   bigint NOT NULL AUTO_INCREMENT,
    name varchar(255),
    PRIMARY KEY (id)
    ) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS user_coupon_entity
(
    id                bigint NOT NULL AUTO_INCREMENT,
    used              bit    NOT NULL,
    coupon_id         bigint NOT NULL,
    expired_date_time datetime(6),
    used_date_time    datetime(6),
    user_id           bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_user_coupon_user FOREIGN KEY (user_id) REFERENCES user_entity (id),
    CONSTRAINT fk_user_coupon_coupon FOREIGN KEY (coupon_id) REFERENCES coupon_entity (id)
    ) ENGINE = InnoDB;
