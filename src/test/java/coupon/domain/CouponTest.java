package coupon.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CouponTest {

    private String name;
    private Integer discountAmount;
    private Integer minOrderAmount;
    private Category category;
    private LocalDate startDate;
    private LocalDate endDate;

    @BeforeEach
    void setUp() {
        name = "coupon_test";
        discountAmount = 3000;
        minOrderAmount = 100000;
        category = Category.FASHION;
        startDate = LocalDate.of(2024, 1, 1);
        endDate = LocalDate.of(2024, 12, 31);
    }

    @Test
    @DisplayName("적절한 값으로 새로운 쿠폰을 생성할 때 예외가 발생하지 않는다.")
    void coupon_createNew() {
        //when, then
        assertDoesNotThrow(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate));
    }

    @CsvSource({"1", "123451234512345123451234512345"})
    @ParameterizedTest
    @DisplayName("쿠폰 이름은 1~30자까지 가능하다.")
    void coupon_create_withVariousName(String name) {
        //when, then
        assertDoesNotThrow(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate));
    }

    @CsvSource({"12345123451234512345123451234512345123451234512345", "1234512345123451234512345123451"})
    @NullAndEmptySource
    @ParameterizedTest
    @DisplayName("쿠폰 이름은 비거나 30자가 넘을 수 없다.")
    void coupon_create_errorWithVariousName(String name) {
        //when, then
        assertThrows(IllegalArgumentException.class, () -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate));
    }

    @CsvSource({"1000, 10000", "5500, 50000", "10000, 100000"})
    @ParameterizedTest
    @DisplayName("할인 금액은 1,000 ~ 10,000원까지 가능하다.")
    void coupon_create_withVariousDiscountAmount(Integer discountAmount, Integer minOrderAmount) {
        //when, then
        assertDoesNotThrow(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate));
    }

    @CsvSource({"1", "999", "1400", "9800", "10001", "200000"})
    @NullSource
    @ParameterizedTest
    @DisplayName("할인 금액은 500원 단위이며 1,000원 미만이거나 10,000원 초과일 수 없다.")
    void coupon_create_errorWithVariousDiscountAmount(Integer discountAmount) {
        //when, then
        assertThrows(IllegalArgumentException.class, () -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate));
    }

    @CsvSource({"1000, 5000", "1000, 5001", "3000, 99999", "10000, 100000"})
    @ParameterizedTest
    @DisplayName("최소 주문 금액은 5,000 ~ 100,000원까지 가능하다.")
    void coupon_create_withVariousMinOrderAmount(Integer discountAmount, Integer minOrderAmount) {
        //when, then
        assertDoesNotThrow(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate));
    }

    @CsvSource({"1", "4999", "100001", "2000000"})
    @NullSource
    @ParameterizedTest
    @DisplayName("최소 주문 금액은 5,000원 미만이거나 100,000원 초과일 수 없다.")
    void coupon_create_errorWithVariousMinOrderAmount(Integer minOrderAmount) {
        //when, then
        assertThrows(IllegalArgumentException.class, () -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate));
    }

    @CsvSource({"3000, 100000", "1000, 10000", "2000, 10000"})
    @ParameterizedTest
    @DisplayName("할인율(할인금액/최소 주문 금액)은 3 ~ 20%까지 가능하다.")
    void coupon_create_withVariousDiscountRate(Integer discountAmount, Integer minOrderAmount) {
        //when, then
        assertDoesNotThrow(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate));
    }

    @CsvSource({"1000, 100000", "2500, 10000", "2100, 10000"})
    @ParameterizedTest
    @DisplayName("할인율은 3% 미만이거나 20% 초과일 수 없다.")
    void coupon_create_errorWithVariousDiscountRate(Integer discountAmount, Integer minOrderAmount) {
        //when, then
        assertThrows(IllegalArgumentException.class, () -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate));
    }

    @EnumSource(Category.class)
    @ParameterizedTest
    @DisplayName("카테고리는 4가지 중 하나만 선택 가능하다.")
    void coupon_create_withVariousCategory(Category category) {
        //when, then
        assertDoesNotThrow(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate));
    }

    @CsvSource({"2024-10-21, 2024-10-21", "2024-10-19, 2024-10-22"})
    @ParameterizedTest
    @DisplayName("시작일은 종료일과 동일하거나 더 이전이어야 한다.")
    void coupon_create_withVariousDuration(LocalDate startDate, LocalDate endDate) {
        //when, then
        assertDoesNotThrow(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate));
    }

    @CsvSource({"2024-10-23, 2024-10-22", "2025-10-31, 2024-10-31"})
    @ParameterizedTest
    @DisplayName("시작일이 종료일보다 이후일 수 없다.")
    void coupon_create_errorWithVariousDuration(LocalDate startDate, LocalDate endDate) {
        //when, then
        assertThrows(IllegalArgumentException.class, () -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate));
    }

    @CsvSource({"2024-10-22, 2024-10-22, true", "2024-10-31, 2024-11-01, false"})
    @ParameterizedTest
    @DisplayName("시작일과 종료일을 비교할 수 있다.")
    void isSameDate(LocalDate startDate, LocalDate endDate, boolean expected) {
        //given
        Coupon coupon = new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate);

        //when
        boolean result = coupon.isSameDate();

        //then
        assertThat(result).isEqualTo(expected);
    }

    @CsvSource({
            "2024-10-22, 2024-10-22, 2024-10-22T00:00:00.000000, true",
            "2024-10-22, 2024-10-22, 2024-10-22T00:00:00.000001, true",
            "2024-10-22, 2024-10-22, 2024-10-22T23:59:59.9999999, false",
            "2024-10-31, 2024-11-03, 2024-11-01T12:30:30.001234, true",
            "2024-10-31, 2024-11-03, 2024-11-03T23:59:59.999999, true",
            "2024-10-31, 2024-11-03, 2024-11-03T23:59:59.9999999, false",
            "2024-10-31, 2024-11-03, 2024-11-04T00:00:00, false"
    })
    @ParameterizedTest
    @DisplayName("쿠폰 발급 가능 여부를 확인할 수 있다.")
    void isGrantable(LocalDate startDate, LocalDate endDate, LocalDateTime requestTime, boolean expected) {
        //given
        Coupon coupon = new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate);

        //when
        boolean result = coupon.isGrantable(requestTime);

        //then
        assertThat(result).isEqualTo(expected);
    }
}
