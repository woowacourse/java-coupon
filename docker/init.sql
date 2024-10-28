-- 쿠폰 테이블 생성
create table IF NOT EXISTS coupon(
                        discount_price_amount decimal(38,2),
                        minimum_order_price_amount decimal(38,2),
                        end_at datetime(6),
                        started_at datetime(6),
                        id VARCHAR(36),
                        name varchar(255),
                        category enum ('FASHION','HOME_APPLIANCE','FURNITURE','FOOD'),
                        primary key (id)
) engine=InnoDB;
create table IF NOT EXISTS member_coupon(
                               isUsed bit not null,
                               expiredAt datetime(6),
                               id VARCHAR(36),
                               issuedAt datetime(6),
                               couponId VARCHAR(36),
                               memberId bigint(16),
                               primary key (id)
) engine=InnoDB;

create table IF NOT EXISTS member(
                               id bigint not null auto_increment,
                               primary key (id)
) engine=InnoDB;
