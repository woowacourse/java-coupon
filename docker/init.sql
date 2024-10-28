use coupon;

create table if not exists Coupon (
    id bigint not null auto_increment,
    name varchar(255),
    productCategory tinyint,
    discountAmount integer,
    minOrderAmount integer,
    issueStartedAt datetime(6),
    issueEndedAt datetime(6),
    primary key (id)
);

create table if not exists Member (
    id bigint not null auto_increment,
    name varchar(255),
    primary key (id)
);

create table if not exists MemberCoupon (
    id bigint not null auto_increment,
    member_id bigint,
    coupon_id bigint,
    isUsed bit,
    endedAt datetime(6),
    issuedAt datetime(6),
    primary key (id),
    foreign key (member_id) references Member(id),
    foreign key (coupon_id) references Coupon(id)
);
