package coupon.coupon.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import coupon.advice.DomainException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Coupon 도메인 테스트")
class CouponTest {

    @DisplayName("쿠폰 이름이 비어 있으면 예외가 발생한다.")
    @Test
    void throwExceptionWhenCouponNameIsEmpty() {
        // given
        String name = "";
        int discountAmount = 5000;
        int minimumOrderPrice = 20000;
        CouponCategory couponCategory = CouponCategory.FASHION;
        LocalDateTime issueStartedAt = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        LocalDateTime issueEndedAt = LocalDateTime.of(2024, 1, 31, 23, 59, 59);

        // when & then
        DomainException exception = assertThrows(DomainException.class,
                () -> new Coupon(name, discountAmount, minimumOrderPrice, couponCategory, issueStartedAt,
                        issueEndedAt));

        assertEquals("쿠폰 이름은 반드시 존재해야 합니다.", exception.getMessage());
    }

    @DisplayName("할인 금액이 500원 단위가 아니면 예외가 발생한다.")
    @Test
    void throwExceptionWhenDiscountAmountIsNotDivisibleBy500() {
        // given
        String name = "할인 쿠폰";
        int discountAmount = 4050;
        int minimumOrderPrice = 20000;
        CouponCategory couponCategory = CouponCategory.FASHION;
        LocalDateTime issueStartedAt = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        LocalDateTime issueEndedAt = LocalDateTime.of(2024, 1, 31, 23, 59, 59);

        // when & then
        DomainException exception = assertThrows(DomainException.class,
                () -> new Coupon(name, discountAmount, minimumOrderPrice, couponCategory, issueStartedAt,
                        issueEndedAt));

        assertEquals("할인 금액은 500원 단위로 설정해야 합니다.", exception.getMessage());
    }

    @DisplayName("최소 주문 금액이 5000원 미만이면 예외가 발생한다.")
    @Test
    void throwExceptionWhenMinimumOrderPriceIsLessThan5000() {
        // given
        String name = "할인 쿠폰";
        int discountAmount = 5000;
        int minimumOrderPrice = 4000; // 5000원 미만
        CouponCategory couponCategory = CouponCategory.FASHION;
        LocalDateTime issueStartedAt = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        LocalDateTime issueEndedAt = LocalDateTime.of(2024, 1, 31, 23, 59, 59);

        // when & then
        DomainException exception = assertThrows(DomainException.class,
                () -> new Coupon(name, discountAmount, minimumOrderPrice, couponCategory, issueStartedAt,
                        issueEndedAt));

        assertEquals("최소 주문 금액은 5000원 이상, 100000원 이하여야 합니다.", exception.getMessage());
    }

    @DisplayName("발급 종료일이 시작일보다 이전이면 예외가 발생한다.")
    @Test
    void throwExceptionWhenIssueEndedBeforeIssueStarted() {
        // given
        String name = "할인 쿠폰";
        int discountAmount = 4000;
        int minimumOrderPrice = 20000;
        CouponCategory couponCategory = CouponCategory.FASHION;
        LocalDateTime issueStartedAt = LocalDateTime.of(2024, 1, 31, 0, 0, 0);
        LocalDateTime issueEndedAt = LocalDateTime.of(2024, 1, 1, 23, 59, 59);

        // when & then
        DomainException exception = assertThrows(DomainException.class,
                () -> new Coupon(name, discountAmount, minimumOrderPrice, couponCategory, issueStartedAt,
                        issueEndedAt));

        assertEquals("발급 시작일은 종료일보다 이전이어야 합니다.", exception.getMessage());
    }
}
