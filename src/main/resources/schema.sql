create database coupon;


create table COUPON (
    DISCOUNT_AMOUNT bigint not null,
    END_AT datetime(6) not null,
    ID bigint not null auto_increment,
    PAYMENT_ID bigint,
    START_AT datetime(6) not null,
    COUPON_NAME varchar(255) not null,
    primary key (ID)
) engine=InnoDB

create table MEMBER (
    ID bigint not null auto_increment,
    USERNAME varchar(255) not null,
    primary key (ID)
) engine=InnoDB

create table PAYMENT (
    ID bigint not null auto_increment,
    PRICE bigint not null,
    category enum ('FASHION','APPLIANCE','FURNITURE','FOOD'),
    primary key (ID)
) engine=InnoDB


alter table COUPON
add constraint UK_ixi9q4v2im2tm0rutrmwb61rt unique (COUPON_NAME)

 alter table COUPON
add constraint FK4qhoccm36wusi0qgg5ow85l79
foreign key (PAYMENT_ID)
references PAYMENT (ID)
