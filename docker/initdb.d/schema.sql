create database if not exists coupon;

use coupon;

create table coupon
(
    id                  bigint       not null auto_increment,
    name                varchar(255) not null,
    category            enum ('FASHION','APPLIANCE','FURNITURE','GROCERY'),
    minimum_order_price bigint       not null,
    discount_amount     bigint       not null,
    issue_start_at      datetime(6)  not null,
    issue_end_at        datetime(6)  not null,
    primary key (id)
) engine = InnoDB;

create table member
(
    id   bigint       not null auto_increment,
    name varchar(255) not null,
    primary key (id)
) engine = InnoDB;

create table member_coupon
(
    id        bigint      not null auto_increment,
    member_id bigint      not null,
    coupon_id bigint      not null,
    used      bit         not null,
    issued_at datetime(6) not null,
    expire_at datetime(6) not null,
    primary key (id),
    foreign key (member_id) references member (id),
    foreign key (coupon_id) references coupon (id)
) engine = InnoDB;
