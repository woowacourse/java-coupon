package coupon.domain.membercoupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponName;
import coupon.domain.coupon.DiscountAmount;
import coupon.domain.coupon.IssuancePeriod;
import coupon.domain.coupon.MinimumOrderAmount;
import coupon.domain.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @Test
    @DisplayName("회원에게 발급된 쿠폰은 발급일 포함 7일 동안 사용 가능하다.")
    void memberCouponExpiration() {
        LocalDateTime createdAt = LocalDateTime.now().minusDays(8);
        Coupon coupon = new Coupon(
                1L,
                new CouponName("쿠폰"),
                new DiscountAmount(new BigDecimal(1_000)),
                new MinimumOrderAmount(new BigDecimal(30_000)),
                Category.FOOD,
                new IssuancePeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));

        assertThatThrownBy(() -> new MemberCoupon(1L, coupon.getId(), new Member(1L), false, createdAt))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
