package coupon.coupon.domain;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import coupon.coupon.CouponException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class DiscountAmountTest {

    @DisplayName("할인 금액이 500원 단위가 아니면 예외가 발생한다.")
    @Test
    void cannotCreateIfWrongDiscountAmountUnit() {
        // given
        String name = "coupon";
        int discountAmount = 1100;
        int minimumOrderAmount = 5000;
        Category category = Category.FASHION;
        LocalDate startAt = LocalDate.now();
        LocalDate endAt = LocalDate.now().plusDays(1);

        // when & then
        assertThatThrownBy(() -> new Coupon(name, discountAmount, minimumOrderAmount, category, startAt, endAt))
                .isInstanceOf(CouponException.class)
                .hasMessage("할인 금액은 500원 단위로 설정할 수 있습니다.");
    }

    @DisplayName("할인 금액이 1000원 미만이면 예외가 발생한다.")
    @Test
    void cannotCreateIfDiscountAmountUnder() {
        // given
        String name = "coupon";
        int discountAmount = 500;
        int minimumOrderAmount = 5000;
        Category category = Category.FASHION;
        LocalDate startAt = LocalDate.now();
        LocalDate endAt = LocalDate.now().plusDays(1);

        // when & then
        assertThatThrownBy(() -> new Coupon(name, discountAmount, minimumOrderAmount, category, startAt, endAt))
                .isInstanceOf(CouponException.class)
                .hasMessage("할인 금액은 1000원 이상, 10000원 이하이어야 합니다.");
    }

    @DisplayName("할인 금액이 10000원 초과이면 예외가 발생한다.")
    @Test
    void cannotCreateIfDiscountAmountOver() {
        // given
        String name = "coupon";
        int discountAmount = 10500;
        int minimumOrderAmount = 5000;
        Category category = Category.FASHION;
        LocalDate startAt = LocalDate.now();
        LocalDate endAt = LocalDate.now().plusDays(1);

        // when & then
        assertThatThrownBy(() -> new Coupon(name, discountAmount, minimumOrderAmount, category, startAt, endAt))
                .isInstanceOf(CouponException.class)
                .hasMessage("할인 금액은 1000원 이상, 10000원 이하이어야 합니다.");
    }
}
