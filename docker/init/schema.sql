create database `coupon`;

use `coupon`;

DROP TABLE IF EXISTS `coupon`;

create table `coupon`
(
    `id`                bigint  not null auto_increment,
    `discountAmount`    integer not null,
    `minimumOrderPrice` integer not null,
    primary key (`id`)
) engine=InnoDB
;
