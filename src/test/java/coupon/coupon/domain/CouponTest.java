package coupon.coupon.domain;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import coupon.coupon.CouponException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CouponTest {

    @DisplayName("쿠폰을 생성한다.")
    @Test
    void createCoupon() {
        // given
        String name = "coupon";
        int discountAmount = 1000;
        int minimumOrderAmount = 30000;
        Category category = Category.FASHION;
        LocalDate startAt = LocalDate.now();
        LocalDate endAt = LocalDate.now().plusDays(1);

        // when & then
        assertThatNoException().isThrownBy(() -> new Coupon(name, discountAmount, minimumOrderAmount, category, startAt, endAt));
    }

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

    @DisplayName("할인율이 3% 미만이면 예외가 발생한다.")
    @Test
    void cannotCreateIfDiscountRateUnder() {
        // given
        String name = "coupon";
        int discountAmount = 1000;
        int minimumOrderAmount = 50000;
        Category category = Category.FASHION;
        LocalDate startAt = LocalDate.now();
        LocalDate endAt = LocalDate.now().plusDays(1);

        // when & then
        assertThatThrownBy(() -> new Coupon(name, discountAmount, minimumOrderAmount, category, startAt, endAt))
                .isInstanceOf(CouponException.class)
                .hasMessage("할인율은 3% 이상, 20% 이하이어야 합니다.");
    }

    @DisplayName("할인율이 20% 초과이면 예외가 발생한다.")
    @Test
    void cannotCreateIfDiscountRateOver() {
        // given
        String name = "coupon";
        int discountAmount = 2500;
        int minimumOrderAmount = 10000;
        Category category = Category.FASHION;
        LocalDate startAt = LocalDate.now();
        LocalDate endAt = LocalDate.now().plusDays(1);

        // when & then
        assertThatThrownBy(() -> new Coupon(name, discountAmount, minimumOrderAmount, category, startAt, endAt))
                .isInstanceOf(CouponException.class)
                .hasMessage("할인율은 3% 이상, 20% 이하이어야 합니다.");
    }

    @DisplayName("종료일이 시작일보다 이전이면 예외가 발생한다.")
    @Test
    void cannotCreateIfEndIsBeforeStart() {
        // given
        String name = "coupon";
        int discountAmount = 1000;
        int minimumOrderAmount = 30000;
        Category category = Category.FASHION;
        LocalDate startAt = LocalDate.now();
        LocalDate endAt = LocalDate.now().minusDays(1);

        // when & then
        assertThatThrownBy(() -> new Coupon(name, discountAmount, minimumOrderAmount, category, startAt, endAt))
                .isInstanceOf(CouponException.class)
                .hasMessage("종료일이 시작일보다 앞설 수 없습니다.");
    }
}
