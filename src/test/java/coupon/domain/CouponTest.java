package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CouponTest {

    /*
    쿠폰 이름은 30자 이하여야 한다.
     */

    @DisplayName("쿠폰 이름은 30자 이하여야 한다.")
    @Test
    void couponNameTest() {
        // given
        String couponName = ".".repeat(30);

        // when, then
        assertThatCode(() -> new Coupon(couponName, 1000, 10000))
                .doesNotThrowAnyException();
    }

    @DisplayName("쿠폰 이름은 30자가 넘으면 예외가 발생한다.")
    @Test
    void couponNameTest1() {
        // given
        String couponName = ".".repeat(31);

        // when, then
        assertThatThrownBy(() -> new Coupon(couponName, 1000, 10000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("쿠폰 이름은 30자 이하여야 합니다.");
    }

    @DisplayName("쿠폰 이름은 공백이거나 널이면 예외가 발생한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void couponNameTest2(String couponName) {
        // when, then
        assertThatThrownBy(() -> new Coupon(couponName, 1000, 10000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("쿠폰 이름은 필수입니다.");
    }

    /*
    할인 금액은 1,000원 이상, 10,000원 이하여야 한다. 500원 단위로 설정할 수 있다.
     */

    @DisplayName("할인 금액은 1,000원 이상이거나 10,000원 이하여야 한다.")
    @CsvSource({
            "1000, 10000",   // 1,000원
            "10000, 100000"  // 10,000원
    })
    @ParameterizedTest(name = "할인 금액 {0}원")
    void discountAmountTest(int discountAmount, int minimumOrderAmount) {
        // when, then
        assertThatCode(() -> new Coupon("망쵸 쿠폰", discountAmount, minimumOrderAmount))
                .doesNotThrowAnyException();
    }

    @DisplayName("할인 금액은 1,000원 미만이거나 10,000원을 넘으면 예외가 발생한다.")
    @ValueSource(ints = {999, 10001})
    @ParameterizedTest(name = "할인 금액 {0}원")
    void discountAmountTest2(int discountAmount) {
        // when, then
        assertThatThrownBy(() -> new Coupon("망쵸 쿠폰", discountAmount, 5000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("할인 금액은 1,000원 이상 10,000원 이하여야 합니다.");
    }

    @DisplayName("할인 금액은 500원 단위로 설정할 수 있다.")
    @ValueSource(ints = {1500, 2500})
    @ParameterizedTest(name = "할인 금액 {0}원")
    void discountAmountTest4(int discountAmount) {
        // when, then
        assertThatCode(() -> new Coupon("망쵸 쿠폰", discountAmount, 12000))
                .doesNotThrowAnyException();
    }

    @DisplayName("할인 금액은 500원 단위가 아니면 예외가 발생한다.")
    @ValueSource(ints = {1001, 1002, 1033, 1444, 1505, 1666, 1700, 1880, 1090})
    @ParameterizedTest(name = "할인 금액 {0}원")
    void discountAmountTest5(int discountAmount) {
        // when, then
        assertThatThrownBy(() -> new Coupon("망쵸 쿠폰", discountAmount, 10000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("할인 금액은 500원 단위로 설정할 수 있습니다.");
    }

    @DisplayName("할인율은 3% 이상 20% 이하이여야 한다.")
    @CsvSource({
            "3000, 100000",  // 할인율 3%
            "2000, 10000"    // 할인율 20%
    })
    @ParameterizedTest(name = "할인 금액 {0}원, 최소 주문 금액 {1}원")
    void discountRateTest(int discountAmount, int minimumOrderAmount) {
        // when, then
        assertThatCode(() -> new Coupon("망쵸 쿠폰", discountAmount, minimumOrderAmount))
                .doesNotThrowAnyException();
    }

    @DisplayName("할인율은 3% 미만, 20% 넘으면 예외가 발생한다.")
    @CsvSource({
            "2500, 100000",  // 할인율 2%
            "10000, 47000"   // 할인율 21%
    })
    @ParameterizedTest(name = "할인 금액 {0}원, 최소 주문 금액 {1}원")
    void discountRateTest1(int discountAmount, int minimumOrderAmount) {
        // when, then
        assertThatThrownBy(() -> new Coupon("망쵸 쿠폰", discountAmount, minimumOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("할인율은 3% 이상 20% 이하여야 합니다.");
    }

    /*
    최소 주문 금액은 5,000원 이상, 100,000원 이하여야 한다.
     */

    @DisplayName("최소 주문 금액은 5,000원 이상이거나 100,000원 이하여야 한다.")
    @CsvSource({
            "1000, 5000",    // 5,000원
            "10000, 100000"  // 100,000원
    })
    @ParameterizedTest(name = "최소 주문 금액 {0}원")
    void minimumOrderAmountTest(
            int discountAmount,     // 할인율 제한을 피하기 위해 설정
            int minimumOrderAmount
    ) {
        // when, then
        assertThatCode(() -> new Coupon("망쵸 쿠폰", discountAmount, minimumOrderAmount))
                .doesNotThrowAnyException();
    }

    @DisplayName("최소 주문 금액은 5,000원 미만이거나 100,000원을 넘으면 예외가 발생한다.")
    @CsvSource({
            "4999",
            "100001"
    })
    @ParameterizedTest(name = "최소 주문 금액 {0}원")
    void minimumOrderAmountTest2(int minimumOrderAmount) {
        // when, then
        assertThatThrownBy(() -> new Coupon("망쵸 쿠폰", 1000, minimumOrderAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("최소 주문 금액은 5,000원 이상 100,000원 이하여야 합니다.");
    }
}
