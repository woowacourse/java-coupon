create database if not exists coupon;

use coupon;

create table coupon
(
    discount_amount     bigint       not null,
    id                  bigint       not null auto_increment,
    issue_end_at        datetime(6),
    issue_start_at      datetime(6),
    minimum_order_price bigint       not null,
    name                varchar(255) not null,
    category            enum ('FASHION','APPLIANCE','FURNITURE','GROCERY'),
    primary key (id)
) engine = InnoDB;

create table member
(
    id bigint not null auto_increment,
    primary key (id)
) engine = InnoDB;

create table member_coupon
(
    used      bit         not null,
    coupon_id bigint      not null,
    expire_at datetime(6) not null,
    id        bigint      not null auto_increment,
    issued_at datetime(6) not null,
    member_id bigint      not null,
    primary key (id)
) engine = InnoDB;

alter table member_coupon
    add constraint FKkxw7ja7v55gk4a368w3gs6s0j
        foreign key (coupon_id)
            references coupon (id);

alter table member_coupon
    add constraint FKf8n3lu0dfwmhvcikamg220lgg
        foreign key (member_id)
            references member (id)
