package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.exception.CouponException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CouponTest {

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "123456789101234567891012345678910"})
    @DisplayName("쿠폰 이름은 비어있거나 30자를 넘으며 예외를 발생시킨다.")
    void givenInvalidCouponNameThrowException(String couponName) {
        assertThatThrownBy(() -> createCoupon(couponName, 1000, 10000))
                .isInstanceOf(CouponException.class)
                .hasMessageContaining("쿠폰 이름");
    }

    @Test
    @DisplayName("쿠폰 이름이 null이면 예외를 발생시킨다.")
    void givenNullCouponNameThrowException() {
        assertThatThrownBy(() -> createInvalidIssuedDate(null, 1000, 10000))
                .isInstanceOf(CouponException.class)
                .hasMessageContaining("쿠폰 이름");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 100000, 10001})
    @DisplayName("할인 금액이 1000원 미만이거나 10000원 초과면 예외가 발생한다.")
    void givenInvalidDiscountAmountThrowException(int discountAmount) {
        assertThatThrownBy(() -> createCoupon("couponName", discountAmount, 10000))
                .isInstanceOf(CouponException.class)
                .hasMessageContaining("할인 금액");
    }

    @ParameterizedTest
    @ValueSource(ints = {1600, 999})
    @DisplayName("할인 금액이 5000원 단위가 아니면 예외가 발생한다.")
    void givenInvalidDiscountUnitAmountThrowException(int discountAmount) {
        assertThatThrownBy(() -> createCoupon("couponName", discountAmount, 10000))
                .isInstanceOf(CouponException.class)
                .hasMessageContaining("할인 금액");
    }

    @ParameterizedTest
    @ValueSource(ints = {1600, 999})
    @DisplayName("최소 주문 금액이 5000미만, 100000 이하면 예외가 발생한다.")
    void givenInvalidMinimumOrderPriceThrowException(int minimumOrderPrice) {
        assertThatThrownBy(() -> createCoupon("couponName", 1000, minimumOrderPrice))
                .isInstanceOf(CouponException.class)
                .hasMessageContaining("최소 주문 금액");
    }

    @Test
    @DisplayName("발급 시작일보다 발급 종료일이 더 이르면 예외가 발생한다.")
    void givenInvalidMinimumOrderPriceThrowException() {
        assertThatThrownBy(() -> createInvalidIssuedDate("couponName", 1000, 10000))
                .isInstanceOf(CouponException.class)
                .hasMessageContaining("발급 시작", "발급 종료일");
    }

    public Coupon createCoupon(String name, int discountAmount, int minimumOrderPrice) {
        return new Coupon(
                name,
                discountAmount,
                minimumOrderPrice,
                CouponCategory.FOOD,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7)
        );
    }

    public Coupon createInvalidIssuedDate(String name, int discountAmount, int minimumOrderPrice) {
        return new Coupon(
                name,
                discountAmount,
                minimumOrderPrice,
                CouponCategory.FOOD,
                LocalDateTime.now(),
                LocalDateTime.now().minusDays(7)
        );
    }
}
