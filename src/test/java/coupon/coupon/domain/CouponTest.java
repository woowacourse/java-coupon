package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CouponTest {

    @Nested
    @DisplayName("이름 검증")
    class NameValidation {

        @DisplayName("이름이 존재하지 않으면 예외가 발생한다.")
        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "\t", "\n"})
        void throwsException_whenNameIsNullOrBlank(String name) {
            // given
            BigDecimal discountAmount = BigDecimal.valueOf(1000);
            BigDecimal minimumOrderPrice = BigDecimal.valueOf(5_000);
            CouponCategory couponCategory = CouponCategory.FOOD;
            LocalDateTime issueStartedAt = LocalDateTime.of(2024, 10, 16, 0, 0, 0, 0);
            LocalDateTime issueEndedAt = LocalDateTime.of(2024, 10, 26, 23, 59, 59, 999999);

            // when & then
            assertThatThrownBy(() ->
                    new Coupon(name, discountAmount, minimumOrderPrice, couponCategory, issueStartedAt, issueEndedAt))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("이름의 길이가 최대 글자 수를 초과하면 예외가 발생한다.")
        @Test
        void throwsException_whenNameLengthIsInvalid() {
            // given
            String name = "thisname'slengthexceedsthirty!!";
            BigDecimal discountAmount = BigDecimal.valueOf(1_000);
            BigDecimal minimumOrderPrice = BigDecimal.valueOf(5_000);
            CouponCategory couponCategory = CouponCategory.FOOD;
            LocalDateTime issueStartedAt = LocalDateTime.of(2024, 10, 16, 0, 0, 0, 0);
            LocalDateTime issueEndedAt = LocalDateTime.of(2024, 10, 26, 23, 59, 59, 999999);

            // when & then
            assertThatThrownBy(() ->
                    new Coupon(name, discountAmount, minimumOrderPrice, couponCategory, issueStartedAt, issueEndedAt))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("할인 금액 검증")
    class DiscountAmountValidation {

        @DisplayName("할인 금액이 최소 할인 금액 미만이면 예외가 발생한다.")
        @Test
        void throwsException_whenDiscountAmountIsLessThanMinimum() {
            // given
            String name = "냥인의쿠폰";
            BigDecimal discountAmount = BigDecimal.valueOf(999);
            BigDecimal minimumOrderPrice = BigDecimal.valueOf(5_000);
            CouponCategory couponCategory = CouponCategory.FOOD;
            LocalDateTime issueStartedAt = LocalDateTime.of(2024, 10, 16, 0, 0, 0, 0);
            LocalDateTime issueEndedAt = LocalDateTime.of(2024, 10, 26, 23, 59, 59, 999999);

            // when & then
            assertThatThrownBy(() ->
                    new Coupon(name, discountAmount, minimumOrderPrice, couponCategory, issueStartedAt, issueEndedAt))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("할인 금액이 최대 할인 금액을 초과하면 예외가 발생한다.")
        @Test
        void throwsException_whenDiscountAmountExceedsMaximum() {
            // given
            String name = "냥인의쿠폰";
            BigDecimal discountAmount = BigDecimal.valueOf(10_001);
            BigDecimal minimumOrderPrice = BigDecimal.valueOf(5_000);
            CouponCategory couponCategory = CouponCategory.FOOD;
            LocalDateTime issueStartedAt = LocalDateTime.of(2024, 10, 16, 0, 0, 0, 0);
            LocalDateTime issueEndedAt = LocalDateTime.of(2024, 10, 26, 23, 59, 59, 999999);

            // when & then
            assertThatThrownBy(() ->
                    new Coupon(name, discountAmount, minimumOrderPrice, couponCategory, issueStartedAt, issueEndedAt))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("할인 금액이 설정된 단위로 나누어 떨어지지 않으면 예외가 발생한다.")
        @Test
        void throwsException_whenDiscountAmountIsNotInUnit() {
            // given
            String name = "냥인의쿠폰";
            BigDecimal discountAmount = BigDecimal.valueOf(501);
            BigDecimal minimumOrderPrice = BigDecimal.valueOf(5_000);
            CouponCategory couponCategory = CouponCategory.FOOD;
            LocalDateTime issueStartedAt = LocalDateTime.of(2024, 10, 16, 0, 0, 0, 0);
            LocalDateTime issueEndedAt = LocalDateTime.of(2024, 10, 26, 23, 59, 59, 999999);

            // when & then
            assertThatThrownBy(() ->
                    new Coupon(name, discountAmount, minimumOrderPrice, couponCategory, issueStartedAt, issueEndedAt))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("최소 주문 금액 검증")
    class MinimumOrderPriceValidation {

        @DisplayName("최소 주문 금액이 최솟값 미만이면 예외가 발생한다.")
        @Test
        void throwsException_whenMinimumOrderPriceIsLessThenMinimum() {
            // given
            String name = "냥인의쿠폰";
            BigDecimal discountAmount = BigDecimal.valueOf(1_000);
            BigDecimal minimumOrderPrice = BigDecimal.valueOf(4_999);
            CouponCategory couponCategory = CouponCategory.FOOD;
            LocalDateTime issueStartedAt = LocalDateTime.of(2024, 10, 16, 0, 0, 0, 0);
            LocalDateTime issueEndedAt = LocalDateTime.of(2024, 10, 26, 23, 59, 59, 999999);

            // when & then
            assertThatThrownBy(() ->
                    new Coupon(name, discountAmount, minimumOrderPrice, couponCategory, issueStartedAt, issueEndedAt))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("최소 주문 금액이 최댓값을 초과하면 예외가 발생한다.")
        @Test
        void throwsException_whenMinimumOrderPriceExceedsMaximum() {
            // given
            String name = "냥인의쿠폰";
            BigDecimal discountAmount = BigDecimal.valueOf(1_000);
            BigDecimal minimumOrderPrice = BigDecimal.valueOf(100_001);
            CouponCategory couponCategory = CouponCategory.FOOD;
            LocalDateTime issueStartedAt = LocalDateTime.of(2024, 10, 16, 0, 0, 0, 0);
            LocalDateTime issueEndedAt = LocalDateTime.of(2024, 10, 26, 23, 59, 59, 999999);

            // when & then
            assertThatThrownBy(() ->
                    new Coupon(name, discountAmount, minimumOrderPrice, couponCategory, issueStartedAt, issueEndedAt))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("할인율 검증")
    class DiscountRateValidation {

        @DisplayName("할인율이 최소 할인율 미만이면 예외가 발생한다.")
        @Test
        void throwsException_whenDiscountRateIsLessThenMinimum() {
            // given
            String name = "냥인의쿠폰";
            BigDecimal discountAmount = BigDecimal.valueOf(2_500);
            BigDecimal minimumOrderPrice = BigDecimal.valueOf(100_000);
            CouponCategory couponCategory = CouponCategory.FOOD;
            LocalDateTime issueStartedAt = LocalDateTime.of(2024, 10, 16, 0, 0, 0, 0);
            LocalDateTime issueEndedAt = LocalDateTime.of(2024, 10, 26, 23, 59, 59, 999999);

            // when & then
            assertThatThrownBy(() ->
                    new Coupon(name, discountAmount, minimumOrderPrice, couponCategory, issueStartedAt, issueEndedAt))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("할인율이 최대 할인율을 초과하면 예외가 발생한다.")
        @Test
        void throwsException_whenDiscountRateExceedsMaximum() {
            // given
            String name = "냥인의쿠폰";
            BigDecimal discountAmount = BigDecimal.valueOf(2_100);
            BigDecimal minimumOrderPrice = BigDecimal.valueOf(100_000);
            CouponCategory couponCategory = CouponCategory.FOOD;
            LocalDateTime issueStartedAt = LocalDateTime.of(2024, 10, 16, 0, 0, 0, 0);
            LocalDateTime issueEndedAt = LocalDateTime.of(2024, 10, 26, 23, 59, 59, 999999);

            // when & then
            assertThatThrownBy(() ->
                    new Coupon(name, discountAmount, minimumOrderPrice, couponCategory, issueStartedAt, issueEndedAt))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("발급 기간 검증")
    class IssueDateValidation {

        @DisplayName("발급 시작일이 발급 종료일보다 이후면 예외가 발생한다.")
        @Test
        void throwsException_whenIssueStartedAtIsAfterIssueEndedAt() {
            // given
            String name = "냥인의쿠폰";
            BigDecimal discountAmount = BigDecimal.valueOf(1_000);
            BigDecimal minimumOrderPrice = BigDecimal.valueOf(5_000);
            CouponCategory couponCategory = CouponCategory.FOOD;
            LocalDateTime issueStartedAt = LocalDateTime.of(2024, 10, 16, 0, 0, 0, 0);
            LocalDateTime issueEndedAt = LocalDateTime.of(2024, 10, 6, 23, 59, 59, 999999);

            // when & then
            assertThatThrownBy(() ->
                    new Coupon(name, discountAmount, minimumOrderPrice, couponCategory, issueStartedAt, issueEndedAt))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("발급 시작일 시각이 유효하지 않으면 예외가 발생한다.")
        @Test
        void throwException_whenIssueStartTimeIsInvalid() {
            // given
            String name = "냥인의쿠폰";
            BigDecimal discountAmount = BigDecimal.valueOf(1_000);
            BigDecimal minimumOrderPrice = BigDecimal.valueOf(5_000);
            CouponCategory couponCategory = CouponCategory.FOOD;
            LocalDateTime issueStartedAt = LocalDateTime.of(2024, 10, 16, 0, 0, 0, 1);
            LocalDateTime issueEndedAt = LocalDateTime.of(2024, 10, 26, 23, 59, 59, 999999);

            // when & then
            assertThatThrownBy(() ->
                    new Coupon(name, discountAmount, minimumOrderPrice, couponCategory, issueStartedAt, issueEndedAt))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("발급 종료일 시각이 유효하지 않으면 예외가 발생한다.")
        @Test
        void throwsException_whenIssueEndTimeIsInvalid() {
            // given
            String name = "냥인의쿠폰";
            BigDecimal discountAmount = BigDecimal.valueOf(1_000);
            BigDecimal minimumOrderPrice = BigDecimal.valueOf(5_000);
            CouponCategory couponCategory = CouponCategory.FOOD;
            LocalDateTime issueStartedAt = LocalDateTime.of(2024, 10, 16, 0, 0, 0, 0);
            LocalDateTime issueEndedAt = LocalDateTime.of(2024, 10, 26, 23, 59, 59, 999998);

            // when & then
            assertThatThrownBy(() ->
                    new Coupon(name, discountAmount, minimumOrderPrice, couponCategory, issueStartedAt, issueEndedAt))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
