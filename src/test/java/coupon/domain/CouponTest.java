package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CouponTest {

    private String name = "쿠폰이름";
    private long discountAmount = 1_000;
    private long minimumOrderPrice = 10_000;
    private Category category = Category.APPLIANCE;
    private LocalDate issueStartAt = LocalDate.now();
    private LocalDate issueEndAt = issueStartAt.plusDays(1);

    private final ThrowingCallable newCoupon = () -> new Coupon(
            name,
            discountAmount,
            minimumOrderPrice,
            category,
            issueStartAt,
            issueEndAt);

    @Test
    @DisplayName("쿠폰 생성 성공")
    void couponConstruct() {
        assertThatNoException().isThrownBy(newCoupon);
    }

    @Nested
    @DisplayName("쿠폰 이름 검증")
    class NameTest {

        @Test
        void nonNull() {
            name = null;
            assertThatThrownBy(newCoupon)
                    .isInstanceOf(NullPointerException.class)
                    .hasMessage("쿠폰 이름을 입력해야 합니다.");
        }

        @Test
        void nonBlank() {
            name = "";
            assertThatThrownBy(newCoupon)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("쿠폰 이름을 입력해야 합니다.");
        }

        @Test
        void lengthUnder30() {
            name = "w".repeat(31);
            assertThatThrownBy(newCoupon)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("쿠폰 이름의 길이는 30자 이하입니다.");
        }
    }

    @Nested
    @DisplayName("카테고리 검증")
    class CategoryTest {

        @Test
        void nonNull() {
            category = null;
            assertThatThrownBy(newCoupon)
                    .isInstanceOf(NullPointerException.class)
                    .hasMessage("카테고리를 입력해야 합니다.");
        }
    }

    @Nested
    @DisplayName("쿠폰 발급 기간 검증")
    class IssuePeriodTest {

        @Test
        void nonNull() {
            issueStartAt = null;
            issueEndAt = null;
            assertThatThrownBy(newCoupon)
                    .isInstanceOf(NullPointerException.class)
                    .hasMessage("발급 시작일과 종료일을 입력해야 합니다.");
        }

        @Test
        void startDateBeforeEndDate() {
            issueStartAt = LocalDate.of(2024, 11, 29);
            issueEndAt = LocalDate.of(2024, 2, 13);
            assertThatThrownBy(newCoupon)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("발급 시작일은 종료일 이후일 수 없습니다.");
        }
    }

    @Nested
    @DisplayName("기본 할인 정책 검증")
    class DefaultDiscountPolicyTest {

        @Test
        void discountAmountInBound() {
            discountAmount = 500L;
            minimumOrderPrice = 10_500L;
            assertThatThrownBy(newCoupon)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("할인 금액은 1000원 이상, 10000원 이하여야 합니다.");
        }

        @Test
        void disCountAmountUnitMatch() {
            discountAmount = 1_001L;
            minimumOrderPrice = 10_000L;
            assertThatThrownBy(newCoupon)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("할인 금액은 500원 단위로 설정할 수 있습니다.");
        }

        @Test
        void minimumOrderPriceInBound() {
            discountAmount = 4_500L;
            minimumOrderPrice = 100_500L;
            assertThatThrownBy(newCoupon)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("최소 주문 금액은 5000원 이상, 100000원 이하여야 합니다.");
        }

        @Test
        void discountRateInBound() {
            discountAmount = 5_000L;
            minimumOrderPrice = 10_000L;
            assertThatThrownBy(newCoupon)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("할인율은 3% 이상, 20% 이하여야 합니다. 현재 할인율: 50%");
        }
    }
}
