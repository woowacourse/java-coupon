package coupon.coupon.domain;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Coupon {

    private final CouponName couponName;
    private final CouponDiscountAmount couponDiscountAmount; // 정책에 의해 바뀌기 쉬운 것
    private final CouponMinOrderAmount couponMinOrderAmount; // 정책에 의해 바뀌기 쉬운 것
    private final CouponCategory couponCategory;
    private final CouponPeriod couponPeriod;

    public Coupon(String couponName, int couponDiscountAmount, int couponMinOrderAmount,
                  CouponCategory couponCategory, LocalDateTime couponStartDate, LocalDateTime couponEndDate) {
        this.couponName = new CouponName(couponName);
        this.couponDiscountAmount = new CouponDiscountAmount(couponDiscountAmount, couponMinOrderAmount);
        this.couponMinOrderAmount = new CouponMinOrderAmount(couponMinOrderAmount);
        this.couponCategory = couponCategory;
        this.couponPeriod = new CouponPeriod(couponStartDate, couponEndDate);
    }
}
