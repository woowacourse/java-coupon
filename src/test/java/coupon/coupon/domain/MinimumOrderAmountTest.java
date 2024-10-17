package coupon.coupon.domain;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import coupon.coupon.CouponException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class MinimumOrderAmountTest {

    @DisplayName("최소 주문 금액이 5000원 미만이면 예외가 발생한다.")
    @Test
    void cannotCreateIfMinimumOrderAmountUnder() {
        // given
        String name = "coupon";
        int discountAmount = 1000;
        int minimumOrderAmount = 4500;
        Category category = Category.FASHION;
        LocalDate startAt = LocalDate.now();
        LocalDate endAt = LocalDate.now().plusDays(1);

        // when & then
        assertThatThrownBy(() -> new Coupon(name, discountAmount, minimumOrderAmount, category, startAt, endAt))
                .isInstanceOf(CouponException.class)
                .hasMessage("최소 주문 금액은 5000원 이상, 100000원 이하이어야 합니다.");
    }

    @DisplayName("최소 주문 금액이 100000원 초과이면 예외가 발생한다.")
    @Test
    void cannotCreateIfMinimumOrderAmountOver() {
        // given
        String name = "coupon";
        int discountAmount = 1000;
        int minimumOrderAmount = 100500;
        Category category = Category.FASHION;
        LocalDate startAt = LocalDate.now();
        LocalDate endAt = LocalDate.now().plusDays(1);

        // when & then
        assertThatThrownBy(() -> new Coupon(name, discountAmount, minimumOrderAmount, category, startAt, endAt))
                .isInstanceOf(CouponException.class)
                .hasMessage("최소 주문 금액은 5000원 이상, 100000원 이하이어야 합니다.");
    }
}
