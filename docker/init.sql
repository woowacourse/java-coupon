-- 쿠폰 테이블 생성
create table coupon IF NOT EXISTS(
                        discount_price_amount decimal(38,2),
                        minimum_order_price_amount decimal(38,2),
                        end_at datetime(6),
                        started_at datetime(6),
                        id binary(16) not null,
                        name varchar(255),
                        category enum ('FASHION','HOME_APPLIANCE','FURNITURE','FOOD'),
                        primary key (id)
) engine=InnoDB;
create table member_coupon IF NOT EXISTS(
                               isUsed bit not null,
                               expiredAt datetime(6),
                               id bigint not null,
                               issuedAt datetime(6),
                               couponId binary(16),
                               memberId binary(16),
                               primary key (id)
) engine=InnoDB;
