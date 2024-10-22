create database if not exists `coupon`;
use `coupon`;

create table if not exists `member`
(
    `id`   bigint      not null auto_increment,
    `name` varchar(30) not null,
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4;

create table if not exists `coupon`
(
    `id`                   bigint      not null auto_increment,
    `name`                 varchar(30) not null,
    `discount_amount`      int,
    `minimum_order_amount` int,
    `category`             varchar(20),
    `issue_started_at`     datetime,
    `issue_ended_at`       datetime,
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4;

create table if not exists `member_coupon`
(
    `id`         bigint not null auto_increment,
    `coupon_id`  bigint,
    `member_id`  bigint,
    `used`       boolean default false,
    `issued_at`  datetime(6),
    `expired_at` datetime(6),
    primary key (`id`),
    constraint `fk_member_coupon_coupon_id` foreign key (`coupon_id`) references coupon (`id`),
    constraint `fk_member_coupon_member_id` foreign key (`member_id`) references member (`id`)
) engine = innodb
  default charset = utf8mb4;
