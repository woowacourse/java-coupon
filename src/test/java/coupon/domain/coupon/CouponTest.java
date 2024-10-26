package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import coupon.domain.Category;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CouponTest {

    @ParameterizedTest
    @ValueSource(ints = {1000, 6000})
    @DisplayName("범위 내의 할인율로 생성할 수 있다.")
    void discountRate(int discountMount) {
        assertDoesNotThrow(() -> new Coupon(
                new CouponName("총대마켓쿠폰"),
                new DiscountMount(discountMount),
                new MinimumMount(30000),
                Category.FASHION,
                new Period(LocalDate.now(), LocalDate.now().plusDays(1))));
    }

    @Test
    @DisplayName("할인율 최소 할인율 미만일 경우 예외로 처리한다.")
    void underMinimum() {
        assertThatThrownBy(() -> new Coupon(
                new CouponName("총대마켓쿠폰"),
                new DiscountMount(1000),
                new MinimumMount(50000),
                Category.FASHION,
                new Period(LocalDate.now(), LocalDate.now().plusDays(1))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 할인율은 3% 이상, 20% 이하여야 합니다.");
    }

    @Test
    @DisplayName("할인율 최대 할인율 초과일 경우 예외로 처리한다.")
    void overMaximum() {
        assertThatThrownBy(() -> new Coupon(
                new CouponName("총대마켓쿠폰"),
                new DiscountMount(7000),
                new MinimumMount(30000),
                Category.FASHION,
                new Period(LocalDate.now(), LocalDate.now().plusDays(1))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 할인율은 3% 이상, 20% 이하여야 합니다.");
    }
}
