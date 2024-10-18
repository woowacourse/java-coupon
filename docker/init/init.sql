create DATABASE IF NOT EXISTS coupon;

use coupon;

create table coupon (
                        id bigint not null auto_increment,
                        name varchar(255),
                        primary key (id)
);
