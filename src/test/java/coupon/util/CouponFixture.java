package coupon.util;

import coupon.domain.Coupon;
import coupon.domain.CouponCategory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CouponFixture {

    public static Coupon createCoupon() {
        return new Coupon(
                "배민 10% 할인 쿠폰",
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(10000),
                CouponCategory.FOOD,
                LocalDateTime.now().minusDays(2),
                LocalDateTime.now().plusDays(2)
        );
    }

    public static List<Coupon> createCoupons() {
        return List.of(
                new Coupon(
                        "배민 10% 할인 쿠폰",
                        BigDecimal.valueOf(1000),
                        BigDecimal.valueOf(10000),
                        CouponCategory.FOOD,
                        LocalDateTime.now().minusDays(2),
                        LocalDateTime.now().plusDays(2)
                ),
                new Coupon(
                        "배민 20% 할인 쿠폰",
                        BigDecimal.valueOf(4000),
                        BigDecimal.valueOf(20000),
                        CouponCategory.FOOD,
                        LocalDateTime.now().minusDays(1),
                        LocalDateTime.now().plusDays(1)
                ),
                new Coupon(
                        "무신사 15% 할인 쿠폰",
                        BigDecimal.valueOf(6000),
                        BigDecimal.valueOf(40000),
                        CouponCategory.FASHION,
                        LocalDateTime.now().minusDays(3),
                        LocalDateTime.now().plusDays(1)
                ),
                new Coupon(
                        "애플 학생 5% 할인 쿠폰",
                        BigDecimal.valueOf(5000),
                        BigDecimal.valueOf(100000),
                        CouponCategory.ELECTRONICS,
                        LocalDateTime.now().minusDays(1),
                        LocalDateTime.now().plusDays(3)
                ),
                new Coupon(
                        "한샘 신혼 20% 할인 쿠폰",
                        BigDecimal.valueOf(10000),
                        BigDecimal.valueOf(50000),
                        CouponCategory.FURNITURE,
                        LocalDateTime.now().minusDays(5),
                        LocalDateTime.now().plusDays(5)
                )
        );
    }
}
