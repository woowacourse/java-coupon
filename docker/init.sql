CREATE DATABASE IF NOT EXISTS coupon;

USE coupon;

create table coupon
(
    id             bigint                                               not null auto_increment,
    discount_money bigint,
    discount_rate  bigint                                               not null,
    end            datetime(6)                                          not null,
    order_price    bigint                                               not null,
    start          datetime(6)                                          not null,
    name           varchar(255)                                         not null,
    category       enum ('FASHIONS','ELECTRONICS','FURNITURES','FOODS') not null,
    primary key (id)
) engine = InnoDB
