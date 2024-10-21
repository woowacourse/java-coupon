package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponTest {

    @Test
    @DisplayName("사용자에게 발행할 수 있는 쿠폰을 생성할 수 있다.")
    void createCoupon() {
        String name = "coupon";
        int discountAmount = 1000;
        int minimumOrderAmount = 10000;
        Category category = Category.FASHION;

        assertDoesNotThrow(() -> new Coupon(name, discountAmount, minimumOrderAmount, category));
    }

    @Test
    @DisplayName("쿠폰 이름은 반드시 존재해야 한다.")
    void validateCouponNameExist() {
        assertThatThrownBy(() -> new Coupon("", 1000, 10000, Category.FASHION))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름은 반드시 존재해야 합니다.");
    }

    @Test
    @DisplayName("쿠폰 이름은 최대 30자 이하여야 한다.")
    void validateCouponNameLength() {
        assertThatThrownBy(
                () -> new Coupon("toolongcouponnamethatcanthandle", 1000, 10000, Category.FASHION))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름의 길이는 최대 30자 이하여야 합니다.");
    }

    @Test
    @DisplayName("할인 금액은 1,000원 이상이어야 한다.")
    void validateDiscountAmountLowerBound() {
        assertThatThrownBy(() -> new Coupon("coupon", 999, 10000, Category.FASHION))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 1,000원 이상이어야 합니다.");
    }

    @Test
    @DisplayName("할인 금액은 10,000원 이하여야 한다.")
    void validateDiscountAmountUpperBound() {
        assertThatThrownBy(() -> new Coupon("coupon", 10001, 10000, Category.FASHION))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 10,000원 이하여야 합니다.");
    }

    @Test
    @DisplayName("할인 금액은 500원 단위로 설정해야 한다.")
    void validateDiscountAmountUnit() {
        assertThatThrownBy(() -> new Coupon("coupon", 1400, 10000, Category.FASHION))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 500원 단위로 설정해야 합니다.");
    }

    @Test
    @DisplayName("최소 주문 금액은 5,000원 이상이어야 한다.")
    void validateMinimumOrderAmountLowerBound() {
        assertThatThrownBy(() -> new Coupon("coupon", 1000, 4999, Category.FASHION))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 5,000원 이상이어야 합니다.");
    }

    @Test
    @DisplayName("최소 주문 금액은 100,000원 이하여야 한다.")
    void validateMinimumOrderAmountUpperBound() {
        assertThatThrownBy(() -> new Coupon("coupon", 1000, 100001, Category.FASHION))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 100,000원 이하여야 합니다.");
    }

    @Test
    @DisplayName("할인율은 3% 이상 20% 이하여야 한다.")
    void validateDiscountRateRange() {
        assertAll(
                () -> assertThatThrownBy(() -> new Coupon("coupon", 10000, 5000, Category.FASHION))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("잘못된 할인율입니다."),
                () -> assertThatThrownBy(() -> new Coupon("coupon", 10000, 40000, Category.FASHION))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("잘못된 할인율입니다.")
        );
    }
}
