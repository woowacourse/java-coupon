package coupon.support;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.CouponName;
import coupon.domain.DiscountAmount;
import coupon.domain.IssuablePeriod;
import coupon.domain.Member;
import coupon.domain.MinOrderAmount;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Fixture {

    public static Member createMember() {
        return new Member(UUID.randomUUID().toString(), "1234");
    }

    public static Coupon createCoupon() {
        return new Coupon(
                new CouponName("반짝 쿠폰"),
                new DiscountAmount(1000),
                new MinOrderAmount(30000),
                Category.FOOD,
                new IssuablePeriod(LocalDateTime.now(), LocalDateTime.now().plusMonths(1))
        );
    }
}
