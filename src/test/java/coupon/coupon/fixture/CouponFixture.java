package coupon.coupon.fixture;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponCategory;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CouponFixture {

    public static Coupon createFoodCoupon() {
        String name = "냥인의 식품 쿠폰";
        BigDecimal discountAmount = BigDecimal.valueOf(1_000);
        BigDecimal minimumOrderPrice = BigDecimal.valueOf(5_000);
        CouponCategory couponCategory = CouponCategory.FOOD;
        LocalDateTime issueStartedAt = LocalDateTime.of(2024, 10, 16, 0, 0, 0, 0);
        LocalDateTime issueEndedAt = LocalDateTime.of(2024, 10, 26, 23, 59, 59, 999_999_000);
        return new Coupon(name, discountAmount, minimumOrderPrice, couponCategory, issueStartedAt, issueEndedAt);
    }

    public static Coupon createFurnitureCoupon() {
        String name = "냥인의 가구 쿠폰";
        BigDecimal discountAmount = BigDecimal.valueOf(1_000);
        BigDecimal minimumOrderPrice = BigDecimal.valueOf(5_000);
        CouponCategory couponCategory = CouponCategory.FURNITURE;
        LocalDateTime issueStartedAt = LocalDateTime.of(2024, 10, 16, 0, 0, 0, 0);
        LocalDateTime issueEndedAt = LocalDateTime.of(2024, 10, 26, 23, 59, 59, 999_999_000);
        return new Coupon(name, discountAmount, minimumOrderPrice, couponCategory, issueStartedAt, issueEndedAt);
    }
}
