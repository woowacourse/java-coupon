package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.Category;
import coupon.exception.CouponException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponTest {

    @DisplayName("쿠폰 발급 기간이 아니라면 예외가 발생한다.")
    @Test
    void validateDateCouponIssuance() {
        Coupon coupon = new Coupon(
                new CouponName("쿠폰1"),
                new DiscountAmount("1000"),
                new MinimumOrderAmount("33333"),
                Category.ELECTRONICS,
                new IssueDuration(
                        LocalDateTime.of(2024, 1, 1, 1, 0, 0),
                        LocalDateTime.of(2024, 1, 1, 1, 0, 1)
                )
        );
        LocalDateTime issuanceTime = LocalDateTime.of(2024, 1, 2, 0, 0, 0);

        assertThatThrownBy(() -> coupon.validateDateCouponIssuance(issuanceTime))
                .isInstanceOf(CouponException.class);
    }
}
