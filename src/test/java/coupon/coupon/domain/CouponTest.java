package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.coupon.exception.CouponApplicationException;
import coupon.member.domain.Member;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class CouponTest {

    private static final String VALID_COUPON_NAME = "투룻 쿠폰";
    private static final int VALID_DISCOUNT_AMOUNT = 10000;
    private static final int VALID_MIN_ORDER_AMOUNT = 100000;
    private static final Category VALID_CATEGORY = Category.FASHION;
    private static final LocalDateTime VALID_ISSUED_AT = LocalDateTime.now();
    private static final LocalDateTime VALID_EXPIRED_AT = LocalDateTime.now().plusDays(3);
    private static final Member VALID_ISSUER = new Member("리비");

    @DisplayName("유효한 쿠폰 생성")
    @Test
    void createValidCoupon() {
        assertThatCode(
                () -> new Coupon(
                        VALID_COUPON_NAME,
                        VALID_ISSUER,
                        VALID_DISCOUNT_AMOUNT,
                        VALID_MIN_ORDER_AMOUNT,
                        VALID_CATEGORY,
                        VALID_ISSUED_AT,
                        VALID_EXPIRED_AT
                )
        ).doesNotThrowAnyException();
    }

    @DisplayName("쿠폰 이름이 Null인 경우 예외 발생")
    @Test
    void createCouponWithNullName() {
        assertThatThrownBy(
                () -> new Coupon(
                        null,
                        VALID_ISSUER,
                        VALID_DISCOUNT_AMOUNT,
                        VALID_MIN_ORDER_AMOUNT,
                        VALID_CATEGORY,
                        VALID_ISSUED_AT,
                        VALID_EXPIRED_AT
                ))
                .isInstanceOf(CouponApplicationException.class)
                .hasMessage("쿠폰의 이름은 비어있을 수 없습니다");

    }

    @DisplayName("쿠폰 이름이 비어있을 때 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"", "\n", "\t"})
    void createCouponWithEmptyName(String invalidCouponName) {
        assertThatThrownBy(
                () -> new Coupon(
                        invalidCouponName,
                        VALID_ISSUER,
                        VALID_DISCOUNT_AMOUNT,
                        VALID_MIN_ORDER_AMOUNT,
                        VALID_CATEGORY,
                        VALID_ISSUED_AT,
                        VALID_EXPIRED_AT
                ))
                .isInstanceOf(CouponApplicationException.class)
                .hasMessage("쿠폰의 이름은 비어있을 수 없습니다");
    }

    @DisplayName("쿠폰 이름의 길이가 범위를 벗어나는 경우 예외")
    @Test
    void createCouponWithNameExceedingMaxLength() {
        final var length31CouponName = "가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다";
        assertThatThrownBy(
                () -> new Coupon(
                        length31CouponName,
                        VALID_ISSUER,
                        VALID_DISCOUNT_AMOUNT,
                        VALID_MIN_ORDER_AMOUNT,
                        VALID_CATEGORY,
                        VALID_ISSUED_AT,
                        VALID_EXPIRED_AT
                ))
                .isInstanceOf(CouponApplicationException.class)
                .hasMessage("쿠폰 이름의 길이는 1자 이상, 30자 이하여야 합니다: 31");
    }

    @DisplayName("할인 금액이 최소값보다 작을 때 예외 발생")
    @ParameterizedTest
    @ValueSource(ints = {0, 100, 999})
    void createCouponWithLowDiscountAmount(int invalidDiscountAmount) {
        assertThatThrownBy(
                () -> new Coupon(
                        VALID_COUPON_NAME,
                        VALID_ISSUER,
                        invalidDiscountAmount,
                        VALID_MIN_ORDER_AMOUNT,
                        VALID_CATEGORY,
                        VALID_ISSUED_AT,
                        VALID_EXPIRED_AT
                ))
                .isInstanceOf(CouponApplicationException.class)
                .hasMessage("쿠폰 할인 금액은 1000이상, 10000이하여야 합니다.");
    }

    @DisplayName("할인 금액이 단위로 나누어 떨어지지 않을 때 예외 발생")
    @ParameterizedTest
    @ValueSource(ints = {1001, 2005, 2499, 4900})
    void createCouponWithInvalidDiscountUnit(int invalidDiscountAmount) {
        assertThatThrownBy(
                () -> new Coupon(
                        VALID_COUPON_NAME,
                        VALID_ISSUER,
                        invalidDiscountAmount,
                        VALID_MIN_ORDER_AMOUNT,
                        VALID_CATEGORY,
                        VALID_ISSUED_AT,
                        VALID_EXPIRED_AT
                ))
                .isInstanceOf(CouponApplicationException.class)
                .hasMessage("쿠폰 할인 금액은 500으로 나누어 떨어져야 합니다.");
    }

    @DisplayName("할인율이 최소값보다 작을 때 예외 발생")
    @ParameterizedTest
    @CsvSource({
            "1000,200000", // 할인율: 2.9997%
            "1000,200000", // 할인율: 1.5%
            "1000,300000" // 할인율: 1%
    })
    void createCouponWithLowDiscountRatio(int discountAmount, int minimumOrderAmount) {
        assertThatThrownBy(
                () -> new Coupon(
                        VALID_COUPON_NAME,
                        VALID_ISSUER,
                        discountAmount,
                        minimumOrderAmount,
                        VALID_CATEGORY,
                        VALID_ISSUED_AT,
                        VALID_EXPIRED_AT
                )).isInstanceOf(CouponApplicationException.class)
                .hasMessageStartingWith("쿠폰 할인율은 0.03 이상, 0.2이하여야 합니다");
    }

    @DisplayName("최소 주문 금액이 범위를 벗어날 때 예외 발생")
    @ParameterizedTest
    @CsvSource({
            "1000,4999", // 할인율: 2.9997%
            "10000,100001", // 할인율: 1.5%
            "10000,200001" // 할인율: 1%
    })
    void createCouponWithInvalidMinimumOrderAmount(int discountAmount, int minimumOrderAmount) {
        assertThatThrownBy(
                () -> new Coupon(
                        VALID_COUPON_NAME,
                        VALID_ISSUER,
                        discountAmount,
                        minimumOrderAmount,
                        VALID_CATEGORY,
                        VALID_ISSUED_AT,
                        VALID_EXPIRED_AT
                ))
                .isInstanceOf(CouponApplicationException.class)
                .hasMessageStartingWith("최소 주문 금액은 5000 이상, 100000 이하여야 합니다");
    }

    @Test
    @DisplayName("발급 시간이 만료 시간보다 이후일 때 예외 발생")
    void createCouponWithInvalidExpirationPeriod() {
        final var issuedAt = LocalDateTime.now();
        final var invalidExpiredAt = LocalDateTime.now().minusDays(2);
        assertThatThrownBy(
                () -> new Coupon(
                        VALID_COUPON_NAME,
                        VALID_ISSUER,
                        VALID_DISCOUNT_AMOUNT,
                        VALID_MIN_ORDER_AMOUNT,
                        VALID_CATEGORY,
                        issuedAt,
                        invalidExpiredAt
                )
        ).isInstanceOf(CouponApplicationException.class);
    }
}
