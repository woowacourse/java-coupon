CREATE DATABASE IF NOT EXISTS coupon;
USE coupon;

create table coupon
(
    id                  bigint                                                not null auto_increment,
    discount_price      integer                                               not null,
    issue_date          date                                                  not null,
    issue_end_date      date                                                  not null,
    minimum_order_price integer                                               not null,
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    name                varchar(30)                                           not null,
    category            enum ('FASHION','HOME_APPLIANCES','FURNITURE','FOOD') not null,
    primary key (id)
);

create table member
(
    id         bigint       not null auto_increment,
    created_at datetime(6),
    name       varchar(255) not null,
    primary key (id)
);

create table member_coupon
(
    id         bigint      not null auto_increment,
    is_used    bit         not null,
    coupon_id  bigint      not null,
    created_at datetime(6),
    expired_at datetime(6) not null,
    issued_at  datetime(6) not null,
    member_id  bigint      not null,
    primary key (id),
    foreign key (member_id) references member (id) on delete cascade
);
