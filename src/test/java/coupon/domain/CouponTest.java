package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;

import static coupon.domain.Category.FOOD;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CouponTest {

    @Nested
    @DisplayName("쿠폰 생성")
    class CreateCoupon {
        @Test
        @DisplayName("성공")
        void couponCreateSuccess() {
            String name = "name";
            Integer discountAmount = 1000;
            Integer purchaseAmount = 10000;
            LocalDateTime startDate = LocalDateTime.now();
            LocalDateTime endDate = startDate.plusDays(1);
            assertThatNoException().isThrownBy(
                    () -> new Coupon(name, discountAmount, purchaseAmount, FOOD, startDate, endDate));
        }

        @ParameterizedTest
        @DisplayName("실패: 쿠폰의 이름은 1자 이상, 30자 이하여야 한다.")
        @ValueSource(strings = {"", " ", "0123456789012345678901234567890"})
        void nameRange(String name) {
            LocalDateTime startDate = LocalDateTime.now();
            LocalDateTime endDate = startDate.plusDays(1);
            assertThatThrownBy(() -> new Coupon(name, 1000, 10000, FOOD, startDate, endDate))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("쿠폰의 이름은 1자 이상 30자 이하여야 합니다.");
        }

        @Test
        @DisplayName("실패: 할인 금액은 500원 단위로 설정할 수 있다.")
        void discountAmountUnit() {
            Integer discountAmount = 1234;
            LocalDateTime startDate = LocalDateTime.now();
            LocalDateTime endDate = startDate.plusDays(1);
            assertThatThrownBy(() -> new Coupon("coupon", discountAmount, 10000, FOOD, startDate, endDate))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("할인 금액은 500원 단위로 설정할 수 있습니다.");
        }

        @Test
        @DisplayName("실패: 할인 금액은 1_000원 이상, 10_000원 이하여야 한다.")
        void discountAmountRange() {
            Integer discountAmount = 500;
            LocalDateTime startDate = LocalDateTime.now();
            LocalDateTime endDate = startDate.plusDays(1);
            assertThatThrownBy(() -> new Coupon("coupon", discountAmount, 10000, FOOD, startDate, endDate))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("할인 금액은 1_000원 이상, 10_000원 이하여야 합니다.");
        }

        @Test
        @DisplayName("실패: 최소 주문 금액은 5_000원 이상 100_000원 이하여야 한다.")
        void purchaseAmountRange() {
            Integer purchaseAmount = 4999;
            LocalDateTime startDate = LocalDateTime.now();
            LocalDateTime endDate = startDate.plusDays(1);
            assertThatThrownBy(() -> new Coupon("coupon", 1000, purchaseAmount, FOOD, startDate, endDate))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("최소 주문 금액은 5_000원 이상 100_000원 이하여야 합니다.");
        }

        @ParameterizedTest
        @DisplayName("실패: 할인율은 3% 이상, 20% 이하여야 한다.")
        @ValueSource(ints = {1_000, 10_000})
        void discountRateRange(Integer discountAmount) {
            Integer purchaseAmount = 40_000;
            LocalDateTime startDate = LocalDateTime.now();
            LocalDateTime endDate = startDate.plusDays(1);
            assertThatThrownBy(() -> new Coupon("coupon", discountAmount, purchaseAmount, FOOD, startDate, endDate))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("할인율은 3% 이상, 20% 이하여야 합니다.");
        }

        @Test
        @DisplayName("실패: 시작일은 종료일보다 이전이어야 한다.")
        void discountRateRange() {
            LocalDateTime startDate = LocalDateTime.now();
            LocalDateTime endDate = startDate.minusDays(1);
            assertThatThrownBy(() -> new Coupon("coupon", 1000, 10000, FOOD, startDate, endDate))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("시작일은 종료일보다 이전이어야 합니다.");
        }
    }
}
