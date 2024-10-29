CREATE DATABASE IF NOT EXISTS coupon;

USE coupon;

create table if not exists coupon
(
    discount_amount bigint      not null,
    purchase_amount bigint      not null,
    end_date        datetime(6)  not null,
    id              bigint       not null auto_increment,
    start_date      datetime(6)  not null,
    name            varchar(255) not null,
    category        enum ('FASHION','APPLIANCE','FURNITURE','FOOD'),
    primary key (id)
);

create table if not exists member
(
    id   bigint       not null auto_increment,
    name varchar(255) not null,
    primary key (id)
);

create table if not exists member_coupon
(
    end_date   date   not null,
    start_date date   not null,
    used       bit    not null,
    coupon_id  bigint,
    id         bigint not null auto_increment,
    member_id  bigint,
    primary key (id),
    foreign key (member_id) references member (id),
    foreign key (coupon_id) references coupon (id)
);
