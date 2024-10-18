package coupon.application.coupon;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponCategory;
import coupon.domain.coupon.CouponPeriod;
import coupon.domain.coupon.DefaultDiscountPolicy;
import coupon.domain.coupon.Name;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record CouponGenerateRequest(
        String name,
        Integer discountMoney,
        Integer minOrderMoney,
        String category,
        String startDate,
        String endDate
) {

    public Coupon toDomain() {
        LocalDate startDate = parseToDate(startDate());
        LocalDate endDate = parseToDate(endDate());

        return new Coupon(
                new Name(name),
                new DefaultDiscountPolicy(discountMoney, minOrderMoney),
                CouponCategory.valueOf(category),
                new CouponPeriod(startDate, endDate)
        );
    }

    private LocalDate parseToDate(String couponGenerateRequest) {
        return LocalDate.parse(couponGenerateRequest, DateTimeFormatter.ISO_DATE);
    }
}
