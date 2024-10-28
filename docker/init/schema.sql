CREATE DATABASE IF NOT EXISTS coupon;
USE coupon;

DROP TABLE IF EXISTS coupon;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS member_coupon;

CREATE TABLE coupon (
    discountAmount integer not null,
    discountRate integer not null,
    minimumOrderAmount integer not null,
    end_date datetime(6) not null,
    id bigint not null auto_increment,
    start_date datetime(6) not null,
    name varchar(255),
    category enum ('FASHION','ELECTRONICS','FURNITURE','FOOD'),
    primary key (id)
) ENGINE=InnoDB;

CREATE TABLE member_coupon (
    isUsed bit not null,
    coupon_id bigint not null,
    end_date datetime(6) not null,
    expireAt datetime(6),
    id bigint not null auto_increment,
    issuedAt datetime(6),
    member_id bigint not null,
    start_date datetime(6) not null,
    primary key (id)
) ENGINE=InnoDB;

CREATE TABLE member (
    end_date datetime(6) not null,
    id bigint not null auto_increment,
    start_date datetime(6) not null,
    name varchar(255),
    primary key (id)
) ENGINE=InnoDB;

ALTER TABLE member_coupon
    ADD CONSTRAINT FKkxw7ja7v55gk4a368w3gs6s0j
        FOREIGN KEY (coupon_id)
            references coupon (id);
ALTER TABLE member_coupon
    ADD CONSTRAINT FKf8n3lu0dfwmhvcikamg220lgg
        FOREIGN KEY (member_id)
            references member (id)
