CREATE DATABASE IF NOT EXISTS coupon;

USE coupon;

create table if not exist coupon
(
    id             bigint auto_increment primary key,
    name           varchar(255)                                            not null,
    discount_money bigint                                                  not null,
    discount_rate  bigint                                                  not null,
    order_price    bigint                                                  not null,
    category       enum ('fashions', 'electronics', 'furnitures', 'foods') not null,
    start          datetime                                                not null,
    end            datetime                                                not null
);
