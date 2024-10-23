package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import coupon.exception.CouponException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CouponTest {

    private static final String NAME = "coupon";
    private static final LocalDate SINCE = LocalDate.parse("2020-01-01");
    private static final LocalDate UNTIL = LocalDate.parse("2020-01-05");
    private static final String CATEGORY = "FASHION";

    @DisplayName("쿠폰 생성 성공: 이름 경계값")
    @ParameterizedTest
    @ValueSource(strings = {"1", "123456789012345678901234567890"})
    void construct_LegalName(String name) {
        assertDoesNotThrow(() -> new Coupon(name, 1000, 10000, SINCE, UNTIL, CATEGORY));
    }

    @DisplayName("쿠폰 생성 실패: 이름 null, empty, 31자")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"1234567890123456789012345678901"})
    void construct_IllegalName(String name) {
        assertThatThrownBy(() -> new Coupon(name, 1000, 10000, SINCE, UNTIL, CATEGORY))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("쿠폰 생성 성공: 할인 금액 경계값 1,000원, 9,500원, 10,000원")
    @ParameterizedTest
    @CsvSource(value = {"1000,10000", "9500,95000", "10000,100000"})
    void construct_LegalDiscountMoney(long discountMoney, long minimumOrderMoney) {
        assertDoesNotThrow(() -> new Coupon(NAME, discountMoney, minimumOrderMoney, SINCE, UNTIL, CATEGORY));
    }

    @DisplayName("쿠폰 생성 실패: 할인 금액 500원, 10,500원, 9,501원")
    @ParameterizedTest
    @CsvSource(value = {"500,5000", "10500,100000", "9501,95010"})
    void construct_IllegalDiscountMoney(long discountMoney, long minimumOrderMoney) {
        assertThatThrownBy(() -> new Coupon(NAME, discountMoney, minimumOrderMoney, SINCE, UNTIL, CATEGORY))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("쿠폰 생성 성공: 최소 주문 금액 경계값 5,000원, 100,000원")
    @ParameterizedTest
    @CsvSource(value = {"1000,5000", "10000,100000"})
    void construct_LegalMinimumOrderMoney(long discountMoney, long minimumOrderMoney) {
        assertDoesNotThrow(() -> new Coupon(NAME, discountMoney, minimumOrderMoney, SINCE, UNTIL, CATEGORY));
    }

    @DisplayName("쿠폰 생성 실패: 최소 주문 금액 경계값 4,999원, 100,001원")
    @ParameterizedTest
    @CsvSource(value = {"1000,4999", "10000,100001"})
    void construct_IllegalMinimumOrderMoney(long discountMoney, long minimumOrderMoney) {
        assertThatThrownBy(() -> new Coupon(NAME, discountMoney, minimumOrderMoney, SINCE, UNTIL, CATEGORY))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("쿠폰 생성 성공: 할인율 경계값 3%, 20%, 20.99%")
    @ParameterizedTest
    @CsvSource(value = {"1500,50000", "1000,5000", "1500,7143"})
    void construct_LegalDiscountRate(long discountMoney, long minimumOrderMoney) {
        assertDoesNotThrow(() -> new Coupon(NAME, discountMoney, minimumOrderMoney, SINCE, UNTIL, CATEGORY));
    }

    @DisplayName("쿠폰 생성 실패: 할인율 경계값 2.99%, 21%")
    @ParameterizedTest
    @CsvSource(value = {"1500,50001", "1500,7142"})
    void construct_IllegalDiscountRate(long discountMoney, long minimumOrderMoney) {
        assertThatThrownBy(() -> new Coupon(NAME, discountMoney, minimumOrderMoney, SINCE, UNTIL, CATEGORY))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("쿠폰 생성 성공: 당일 시작, 당일 끝")
    @Test
    void construct_LegalDate() {
        assertDoesNotThrow(() -> new Coupon(NAME, 1000, 10000, SINCE, SINCE, CATEGORY));
    }

    @DisplayName("쿠폰 생성 실패: 미래 시작, 과거 끝")
    @Test
    void construct_IllegalDate() {
        assertThatThrownBy(() -> new Coupon(NAME, 1000, 10000, UNTIL, SINCE, CATEGORY))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("시작일 00:00:00.000000에 발급 가능")
    @Test
    void isIssuableAt_True_Start() {
        Coupon coupon = new Coupon(NAME, 1000, 10000,
                LocalDate.parse("2020-01-01"), LocalDate.parse("2020-01-02"), CATEGORY);

        assertThat(coupon.isIssuableAt(LocalDateTime.parse("2020-01-01T00:00:00.000000"))).isTrue();
    }

    @DisplayName("시작일 전날 23:59:59.999999에 발급 불가능")
    @Test
    void isIssuableAt_False_Start() {
        Coupon coupon = new Coupon(NAME, 1000, 10000,
                LocalDate.parse("2020-01-01"), LocalDate.parse("2020-01-02"), CATEGORY);

        assertThat(coupon.isIssuableAt(LocalDateTime.parse("2019-12-31T23:59:59.999999"))).isFalse();
    }

    @DisplayName("종료일 23:59:59.999999에 발급 가능")
    @Test
    void isIssuableAt_True_End() {
        Coupon coupon = new Coupon(NAME, 1000, 10000,
                LocalDate.parse("2020-01-01"), LocalDate.parse("2020-01-02"), CATEGORY);

        assertThat(coupon.isIssuableAt(LocalDateTime.parse("2020-01-02T23:59:59.999999"))).isTrue();
    }

    @DisplayName("종료일 다음날 00:00:00.000000에 발급 불가능")
    @Test
    void isIssuableAt_False_End() {
        Coupon coupon = new Coupon(NAME, 1000, 10000,
                LocalDate.parse("2020-01-01"), LocalDate.parse("2020-01-02"), CATEGORY);

        assertThat(coupon.isIssuableAt(LocalDateTime.parse("2020-01-03T00:00:00.000000"))).isFalse();
    }
}
