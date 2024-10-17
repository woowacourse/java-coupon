insert into coupon(coupon_name,
                   coupon_category,
                   issuable_start_date,
                   issuable_end_date,
                   coupon_discount_amount,
                   coupon_applicable_amount)
values ('1,000원 할인권', 'FOOD', current_timestamp(), current_timestamp(), 1000, 10000);
