create
database if not exists coupon;
use coupon;
create table if not exists Coupon
(
    id              bigint not null auto_increment,
    category        enum ('FASHION','ELECTRONICS','FURNITURE','FOOD'),
    coupon_name     varchar(255),
    discount_price  integer,
    end_date        date,
    start_date      date,
    min_order_price integer,
    primary key (id)
) engine=InnoDB;

create table if not exists IssuedCoupon
(
    id        bigint not null auto_increment,
    couponId  bigint,
    createdAt date,
    expiredAt date,
    isUsed    bit,
    member_id bigint,
    primary key (id)
) engine=InnoDB;

create table if not exists Member
(
    id bigint not null auto_increment,
    primary key (id)
) engine=InnoDB;

alter table IssuedCoupon
    add constraint FKnla2ktgbwhr2vg5r86htlijy6
        foreign key (member_id)
            references Member (id)
