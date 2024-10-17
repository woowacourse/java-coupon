package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import coupon.exception.CouponException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CouponTest {

    private static final String NAME = "coupon";

    @DisplayName("쿠폰 생성 성공: 이름 경계값")
    @ParameterizedTest
    @ValueSource(strings = {"1", "123456789012345678901234567890"})
    void construct_LegalName(String name) {
        assertDoesNotThrow(() -> new Coupon(name, 1000, 10000));
    }

    @DisplayName("쿠폰 생성 실패: 이름 null, empty, 31자")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"1234567890123456789012345678901"})
    void construct_IllegalName(String name) {
        assertThatThrownBy(() -> new Coupon(name, 1000, 10000))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("쿠폰 생성 성공: 할인 금액 경계값 1,000원, 9,500원, 10,000원")
    @ParameterizedTest
    @CsvSource(value = {"1000,10000", "9500,95000", "10000,100000"})
    void construct_LegalDiscountMoney(long discountMoney, long minimumOrderMoney) {
        assertDoesNotThrow(() -> new Coupon(NAME, discountMoney, minimumOrderMoney));
    }

    @DisplayName("쿠폰 생성 실패: 할인 금액 500원, 10,500원, 9,501원")
    @ParameterizedTest
    @CsvSource(value = {"500,5000", "10500,100000", "9501,95010"})
    void construct_IllegalDiscountMoney(long discountMoney, long minimumOrderMoney) {
        assertThatThrownBy(() -> new Coupon(NAME, discountMoney, minimumOrderMoney))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("쿠폰 생성 성공: 최소 주문 금액 경계값 5,000원, 100,000원")
    @ParameterizedTest
    @CsvSource(value = {"1000,5000", "10000,100000"})
    void construct_LegalMinimumOrderMoney(long discountMoney, long minimumOrderMoney) {
        assertDoesNotThrow(() -> new Coupon(NAME, discountMoney, minimumOrderMoney));
    }

    @DisplayName("쿠폰 생성 실패: 최소 주문 금액 경계값 4,999원, 100,001원")
    @ParameterizedTest
    @CsvSource(value = {"1000,4999", "10000,100001"})
    void construct_IllegalMinimumOrderMoney(long discountMoney, long minimumOrderMoney) {
        assertThatThrownBy(() -> new Coupon(NAME, discountMoney, minimumOrderMoney))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("쿠폰 생성 성공: 할인율 경계값 3%, 20%, 20.99%")
    @ParameterizedTest
    @CsvSource(value = {"1500,50000", "1000,5000", "1500,7143"})
    void construct_LegalDiscountRate(long discountMoney, long minimumOrderMoney) {
        assertDoesNotThrow(() -> new Coupon(NAME, discountMoney, minimumOrderMoney));
    }

    @DisplayName("쿠폰 생성 실패: 할인율 경계값 2.99%, 21%")
    @ParameterizedTest
    @CsvSource(value = {"1500,50001", "1500,7142"})
    void construct_IllegalDiscountRate(long discountMoney, long minimumOrderMoney) {
        assertThatThrownBy(() -> new Coupon(NAME, discountMoney, minimumOrderMoney))
                .isInstanceOf(CouponException.class);
    }
}
