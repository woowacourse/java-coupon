package coupon.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.time.LocalDate;
import java.util.stream.Stream;

class CouponTest {

    @Test
    @DisplayName("유효한 쿠폰 생성 테스트")
    void testValidCouponCreation() {
        String name = "Spring Sale";
        int discountAmount = 3_000;
        int minOrderAmount = 20_000;
        String category = "패션";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(10);

        Coupon coupon = new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate);

        assertAll(
                () -> assertThat(coupon.getName()).isEqualTo(name),
                () -> assertThat(coupon.getDiscountAmount()).isEqualTo(discountAmount),
                () -> assertThat(coupon.getMinOrderAmount()).isEqualTo(minOrderAmount),
                () -> assertThat(coupon.getCategory()).isEqualTo(CouponCategory.from(category)),
                () -> assertThat(coupon.getStartDate()).isEqualTo(startDate),
                () -> assertThat(coupon.getEndDate()).isEqualTo(endDate)
        );
    }

    @ParameterizedTest(name = "이름 검증 테스트 - 입력값: \"{0}\"")
    @NullAndEmptySource
    @ValueSource(strings = { "   ", "", " " })
    @DisplayName("이름은 반드시 존재해야 하며 비어있거나 공백일 수 없습니다.")
    void testNameValidation(String name) {
        int discountAmount = 3_000;
        int minOrderAmount = 20_000;
        String category = "패션";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(10);

        assertThatThrownBy(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name must exist and not be empty.");
    }

    @ParameterizedTest(name = "이름 길이 검증 테스트 - 입력 길이: {0}")
    @ValueSource(strings = {
            "This coupon name is definitely more than thirty characters long",
            "Another excessively long coupon name exceeding the limit"
    })
    @DisplayName("이름의 길이는 30자를 초과할 수 없습니다.")
    void testNameLengthExceedsMaximum(String name) {
        int discountAmount = 3_000;
        int minOrderAmount = 20_000;
        String category = "패션";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(10);

        assertThatThrownBy(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name length must be 30 characters or less.");
    }

    @ParameterizedTest(name = "할인 금액 검증 테스트 - 유형: {0}, 값: {1}, 메시지: \"{2}\"")
    @MethodSource("provideDiscountAmountScenarios")
    @DisplayName("할인 금액의 유효성을 검증합니다.")
    void testDiscountAmountValidation(String type, int discountAmount, String expectedMessage) {
        String name = "Sale";
        int minOrderAmount = 20_000;
        String category = "가전";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);

        assertThatThrownBy(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }

    private static Stream<Arguments> provideDiscountAmountScenarios() {
        return Stream.of(
                Arguments.of("BelowMinimum", 500, "Discount amount must be at least 1000 won."),
                Arguments.of("BelowMinimum", 750, "Discount amount must be at least 1000 won."),
                Arguments.of("AboveMaximum", 10_500, "Discount amount must be at most 10000 won."),
                Arguments.of("AboveMaximum", 15_000, "Discount amount must be at most 10000 won."),
                Arguments.of("NotIncrementOf500", 2_750, "Discount amount must be in increments of 500 won."),
                Arguments.of("NotIncrementOf500", 1_250, "Discount amount must be in increments of 500 won.")
        );
    }

    @ParameterizedTest(name = "최소 주문 금액 검증 테스트 - 유형: {0}, 값: {1}, 메시지: \"{2}\"")
    @MethodSource("provideMinOrderAmountScenarios")
    @DisplayName("최소 주문 금액의 유효성을 검증합니다.")
    void testMinOrderAmountValidation(String type, int minOrderAmount, String expectedMessage) {
        String name = "Sale";
        int discountAmount = 5_000;
        String category = "패션";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);

        assertThatThrownBy(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }

    private static Stream<Arguments> provideMinOrderAmountScenarios() {
        return Stream.of(
                Arguments.of("BelowMinimum", 3_000, "Minimum order amount must be at least 5000 won."),
                Arguments.of("BelowMinimum", 4_999, "Minimum order amount must be at least 5000 won."),
                Arguments.of("AboveMaximum", 150_000, "Minimum order amount must be at most 100000 won."),
                Arguments.of("AboveMaximum", 200_000, "Minimum order amount must be at most 100000 won.")
        );
    }

    @ParameterizedTest(name = "할인율 검증 테스트 - 유형: {0}, 할인 금액: {1}, 최소 주문 금액: {2}, 메시지: \"{3}\"")
    @MethodSource("provideDiscountRateScenarios")
    @DisplayName("할인율의 유효성을 검증합니다.")
    void testDiscountRateValidation(String type, int discountAmount, int minOrderAmount, String expectedMessage) {
        String name = "Sale";
        String category = "가구";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);

        assertThatThrownBy(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }

    private static Stream<Arguments> provideDiscountRateScenarios() {
        return Stream.of(
                Arguments.of("BelowMinimum", 1_000, 50_000, "Discount rate must be at least 3%."),
                Arguments.of("BelowMinimum", 1_500, 60_000, "Discount rate must be at least 3%."),
                Arguments.of("AboveMaximum", 2_500, 10_000, "Discount rate must be at most 20%."),
                Arguments.of("AboveMaximum", 4_000, 15_000, "Discount rate must be at most 20%.")
        );
    }

    @ParameterizedTest(name = "카테고리 검증 테스트 - 입력값: \"{0}\"")
    @ValueSource(strings = { "InvalidCategory", "Unknown", "123", "!@#" })
    @DisplayName("카테고리는 유효한 값이어야 합니다.")
    void testInvalidCategory(String category) {
        String name = "Sale";
        int discountAmount = 3_000;
        int minOrderAmount = 20_000;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);

        assertThatThrownBy(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid category.");
    }

    @Test
    @DisplayName("시작일이 종료일 이후일 때 예외 발생 테스트")
    void testStartDateAfterEndDate() {
        String name = "Sale";
        int discountAmount = 3_000;
        int minOrderAmount = 20_000;
        String category = "패션";
        LocalDate startDate = LocalDate.now().plusDays(5);
        LocalDate endDate = LocalDate.now();

        assertThatThrownBy(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Start date must be before or equal to end date.");
    }

    @Test
    @DisplayName("시작일과 종료일이 동일할 때 쿠폰 생성 테스트")
    void testStartDateAndEndDateAreEqual() {
        String name = "One Day Sale";
        int discountAmount = 3_000;
        int minOrderAmount = 20_000;
        String category = "가전";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now(); // 시작일과 동일

        Coupon coupon = new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate);

        assertAll(
                () -> assertThat(coupon.getStartDate()).isEqualTo(startDate),
                () -> assertThat(coupon.getEndDate()).isEqualTo(endDate)
        );
    }

    @ParameterizedTest(name = "날짜 검증 테스트 - 시작일: {0}, 종료일: {1}, 메시지: \"{2}\"")
    @MethodSource("provideNullDates")
    @DisplayName("시작일과 종료일은 null일 수 없습니다.")
    void testNullDates(LocalDate startDate, LocalDate endDate, String expectedMessage) {
        String name = "Sale";
        int discountAmount = 3_000;
        int minOrderAmount = 20_000;
        String category = "가구";

        assertThatThrownBy(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }

    private static Stream<Arguments> provideNullDates() {
        return Stream.of(
                Arguments.of(null, LocalDate.now().plusDays(5), "Start date and end date must not be null."),
                Arguments.of(LocalDate.now(), null, "Start date and end date must not be null.")
        );
    }
}
