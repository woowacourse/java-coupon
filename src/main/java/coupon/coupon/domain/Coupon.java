package coupon.coupon.domain;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Coupon {

    private final CouponName couponName;
    private final int couponDiscountAmount;
    private final int couponMinOrderAmount;
    private final CouponCategory couponCategory;
    private final CouponPeriod couponPeriod;

    public Coupon(String couponName, int couponDiscountAmount, int couponMinOrderAmount,
                  CouponCategory couponCategory, LocalDateTime couponStartDate, LocalDateTime couponEndDate) {
        this.couponName = new CouponName(couponName);
        this.couponDiscountAmount = couponDiscountAmount;
        this.couponMinOrderAmount = couponMinOrderAmount;
        this.couponCategory = couponCategory;
        this.couponPeriod = new CouponPeriod(couponStartDate, couponEndDate);
    }
}
