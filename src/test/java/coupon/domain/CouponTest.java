package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CouponTest {

    @Nested
    @DisplayName("쿠폰 검증 예외 테스트")
    class ValidateExceptionTest {
        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("쿠폰 이름이 비어 있다면 예외를 발생시킨다.")
        void validateNameBlankTest(String name) {
            assertThatThrownBy(
                    () -> new Coupon(name, 1000L, 100L, LocalDate.now(), LocalDate.now(), new Category("카테고리")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("쿠폰 이름은 필수입니다.");
        }

        @Test
        @DisplayName("쿠폰 이름이 30자를 초과하면 예외를 발생시킨다.")
        void validateNameLengthTest() {
            String nameLength31 = "1234567890123456789012345678901";
            assertThatThrownBy(
                    () -> new Coupon(nameLength31, 1000L, 100L, LocalDate.now(), LocalDate.now(),
                            new Category("카테고리")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("쿠폰 이름은 30자 이하여야 합니다.");
        }

        @Test
        @DisplayName("최소 주문 금액이 null인 경우 예외를 발생시킨다.")
        void validateMinimumAmountNullTest() {
            assertThatThrownBy(
                    () -> new Coupon("쿠폰", null, 100L, LocalDate.now(), LocalDate.now(), new Category("카테고리")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("최소 주문 금액은 필수입니다.");
        }

        @ParameterizedTest
        @ValueSource(longs = {-1L, 0L, 4_999L, 100_001L})
        @DisplayName("최소 주문 금액이 5000원 미만 또는 100000원 초과인 경우 예외를 발생시킨다.")
        void validateMinimumAmountRangeTest(Long minimumAmount) {
            assertThatThrownBy(
                    () -> new Coupon("쿠폰", minimumAmount, 100L, LocalDate.now(), LocalDate.now(), new Category("카테고리")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("최소 주문 금액은 5000원 이상 100000원 이하여야 합니다.");
        }

        @Test
        @DisplayName("할인 금액이 null인 경우 예외를 발생시킨다.")
        void validateDiscountAmountNullTest() {
            assertThatThrownBy(
                    () -> new Coupon("쿠폰", 5_000L, null, LocalDate.now(), LocalDate.now(), new Category("카테고리")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("할인 금액은 필수입니다.");
        }

        @ParameterizedTest
        @ValueSource(longs = {-1L, 0L, 999L, 10_001L})
        @DisplayName("할인 금액이 1000원 미만 또는 10000원 초과인 경우 예외를 발생시킨다.")
        void validateDiscountAmountRangeTest(Long discountAmount) {
            assertThatThrownBy(
                    () -> new Coupon("쿠폰", 5_000L, discountAmount, LocalDate.now(), LocalDate.now(),
                            new Category("카테고리")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("할인 금액은 1000원 이상 10000원 이하여야 합니다.");
        }

        @ParameterizedTest
        @ValueSource(longs = {1001L, 1499L, 9999L})
        @DisplayName("할인 금액이 500원 단위가 아닌 경우 예외를 발생시킨다.")
        void validateDiscountAmountUnitTest(Long discountAmount) {
            assertThatThrownBy(
                    () -> new Coupon("쿠폰", 5_000L, discountAmount, LocalDate.now(), LocalDate.now(),
                            new Category("카테고리")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("할인 금액은 500원 단위로 입력해야 합니다.");
        }

        @ParameterizedTest
        @MethodSource("provideMinimumAmountAndDiscountAmountInvalidDiscountRate")
        @DisplayName("최소 주문 금액과 할인 금액으로 할인율이 3% 미만 또는 20% 초과인 경우 예외를 발생시킨다.")
        void validateDiscountRateTest(Long minimumAmount, Long discountAmount) {
            assertThatThrownBy(
                    () -> new Coupon("쿠폰", minimumAmount, discountAmount, LocalDate.now(), LocalDate.now(),
                            new Category("카테고리")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("할인율은 3 이상 20 이하여야 합니다.");
        }

        static Stream<Arguments> provideMinimumAmountAndDiscountAmountInvalidDiscountRate() {
            return Stream.of(
                    Arguments.of(100_000L, 2_500L), // 2.5%
                    Arguments.of(10_000L, 2_500L) // 25.00%
            );
        }

        @Test
        @DisplayName("발급 시작일이 null인 경우 예외를 발생시킨다.")
        void validateStartIssueDateNullTest() {
            assertThatThrownBy(() -> new Coupon("쿠폰", 5_000L, 1_000L, null, LocalDate.now(), new Category("카테고리")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("발급 기간은 필수입니다.");
        }

        @Test
        @DisplayName("발급 종료일이 null인 경우 예외를 발생시킨다.")
        void validateEndIssueDateNullTest() {
            assertThatThrownBy(() -> new Coupon("쿠폰", 5_000L, 1_000L, LocalDate.now(), null, new Category("카테고리")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("발급 기간은 필수입니다.");
        }

        @Test
        @DisplayName("발급 시작일이 발급 종료일보다 빠른 경우 예외를 발생시킨다.")
        void validateIssueDateRangeTest() {
            assertThatThrownBy(
                    () -> new Coupon("쿠폰", 5_000L, 1_000L, LocalDate.now().plusDays(1), LocalDate.now(),
                            new Category("카테고리")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("발급 시작일은 종료일보다 빠를 수 없습니다.");
        }

        @Test
        @DisplayName("카테고리가 null인 경우 예외를 발생시킨다.")
        void validateCategoryNullTest() {
            assertThatThrownBy(() -> new Coupon("쿠폰", 5_000L, 1_000L, LocalDate.now(), LocalDate.now(), null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("카테고리는 필수입니다.");
        }
    }

    @Nested
    @DisplayName("쿠폰 생성 성공 테스트")
    class createCouponSuccessTest {
        @ParameterizedTest
        @ValueSource(strings = {"1", "123456789012345678901234567890"})
        @DisplayName("쿠폰 이름이 1~30자인 경우 생성에 성공한다.")
        void createCouponNameLengthTest(String name) {
            assertThatCode(
                    () -> new Coupon(name, 5_000L, 1_000L, LocalDate.now(), LocalDate.now(), new Category("카테고리")))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource("provideMinimumAmountAndDiscountAmountWithValidMinimumAmount")
        @DisplayName("최소 주문 금액이 5,000원 이상, 100,000원 이하인 경우 생성에 성공한다.")
        void createCouponMinimumAmountTest(Long minimumAmount, Long discountAmount) {
            assertThatCode(
                    () -> new Coupon("쿠폰", minimumAmount, discountAmount, LocalDate.now(), LocalDate.now(),
                            new Category("카테고리")))
                    .doesNotThrowAnyException();
        }

        static Stream<Arguments> provideMinimumAmountAndDiscountAmountWithValidMinimumAmount() {
            return Stream.of(
                    Arguments.of(5_000L, 1_000L),
                    Arguments.of(100_000L, 10_000L)
            );
        }

        @ParameterizedTest
        @MethodSource("provideMinimumAmountAndDiscountAmountWithValidDiscountAmount")
        @DisplayName("할인 금액이 1,000원 이상, 10,000원 이하인 경우 생성에 성공한다.")
        void createCouponDiscountAmountTest(Long minimumAmount, Long discountAmount) {
            assertThatCode(
                    () -> new Coupon("쿠폰", minimumAmount, discountAmount, LocalDate.now(), LocalDate.now(),
                            new Category("카테고리")))
                    .doesNotThrowAnyException();
        }

        static Stream<Arguments> provideMinimumAmountAndDiscountAmountWithValidDiscountAmount() {
            return Stream.of(
                    Arguments.of(5_000L, 1_000L),
                    Arguments.of(100_000L, 10_000L)
            );
        }

        @ParameterizedTest
        @ValueSource(longs = {1_000L, 1_500L, 2_000L})
        @DisplayName("할인 금액이 500원 단위인 경우 생성에 성공한다.")
        void createCouponDiscountAmountUnitTest(Long discountAmount) {
            assertThatCode(
                    () -> new Coupon("쿠폰", 10_000L, discountAmount, LocalDate.now(), LocalDate.now(),
                            new Category("카테고리")))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource("provideMinimumAmountAndDiscountAmountWithValidDiscountRate")
        @DisplayName("최소 주문 금액과 할인 금액으로 할인율이 3% 이상, 20% 이하인 경우 생성에 성공한다.")
        void createCouponDiscountRateTest(Long minimumAmount, Long discountAmount) {
            assertThatCode(
                    () -> new Coupon("쿠폰", minimumAmount, discountAmount, LocalDate.now(), LocalDate.now(),
                            new Category("카테고리")))
                    .doesNotThrowAnyException();
        }

        static Stream<Arguments> provideMinimumAmountAndDiscountAmountWithValidDiscountRate() {
            return Stream.of(
                    Arguments.of(100_000L, 3_000L), // 3%
                    Arguments.of(10_000L, 2_000L) // 20%
            );
        }

        @Test
        @DisplayName("발급 시작일과 종료일이 같은 경우 생성에 성공한다.")
        void createCouponIssueDateSameTest() {
            assertThatCode(
                    () -> new Coupon("쿠폰", 5_000L, 1_000L, LocalDate.now(), LocalDate.now(), new Category("카테고리")))
                    .doesNotThrowAnyException();
        }
    }
}
