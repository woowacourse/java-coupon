package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponTest {

    @Test
    @DisplayName("할인율이 3% 미만이면 예외가 발생한다.")
    void should_throw_exception_when_discount_rate_less_than_3_percent() {
        // given
        CouponName couponName = new CouponName("쿠폰");
        DiscountAmount discountAmount = new DiscountAmount(2500);
        MinimumOrderAmount minimumOrderAmount = new MinimumOrderAmount(100000);
        IssuablePeriod issuablePeriod
                = new IssuablePeriod(LocalDate.parse("2024-10-18"), LocalDate.parse("2024-10-18"));

        // when & then
        assertThatThrownBy(() -> {
            new Coupon(couponName, discountAmount, minimumOrderAmount, Category.FOODS, issuablePeriod);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 3% 이상이어야 합니다.");
    }

    @Test
    @DisplayName("할인율이 20%를 초과하면 예외가 발생한다.")
    void should_throw_exception_when_discount_rate_more_than_20_percent() {
        // given
        CouponName couponName = new CouponName("쿠폰");
        DiscountAmount discountAmount = new DiscountAmount(10000);
        MinimumOrderAmount minimumOrderAmount = new MinimumOrderAmount(47619);
        IssuablePeriod issuablePeriod
                = new IssuablePeriod(LocalDate.parse("2024-10-18"), LocalDate.parse("2024-10-18"));

        // when & then
        assertThatThrownBy(() -> {
            new Coupon(couponName, discountAmount, minimumOrderAmount, Category.FOODS, issuablePeriod);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 20% 이하여야 합니다.");
    }
}
