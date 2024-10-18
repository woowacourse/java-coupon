package coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CouponTest {

    @Test
    @DisplayName("쿠폰 생성 성공")
    void testValidCouponCreation() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(5);

        Coupon coupon = new Coupon("테스트 쿠폰", 1000, 5000, Category.FASHION, start, end);

        assertThat(coupon.getName()).isEqualTo("테스트 쿠폰");
        assertThat(coupon.getDiscountAmount()).isEqualTo(1000);
        assertThat(coupon.getMinimumOrderAmount()).isEqualTo(5000);
        assertThat(coupon.getCategory()).isEqualTo(Category.FASHION);
        assertThat(coupon.getIssuanceStart()).isEqualTo(start);
        assertThat(coupon.getIssuanceEnd()).isEqualTo(end);
    }

    @Test
    @DisplayName("쿠폰 생성 실패 : 이름이 30자를 초과할 경우")
    void testCouponNameTooLong() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(5);

        assertThatThrownBy(() -> new Coupon("이름".repeat(30), 1000, 5000, Category.FASHION, start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름은 필수이며 최대 30자 이하이어야 합니다.");
    }

    @Test
    @DisplayName("쿠폰 생성 실패 : 할인 금액이 1000원 미만일 경우")
    void testDiscountAmountTooLow() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(5);

        assertThatThrownBy(() -> new Coupon("테스트 쿠폰", 500, 5000, Category.FASHION, start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 1,000원 이상 10,000원 이하이며 500원 단위여야 합니다.");
    }

    @Test
    @DisplayName("쿠폰 생성 실패 : 할인 금액이 10000원을 초과할 경우")
    void testDiscountAmountTooHigh() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(5);

        assertThatThrownBy(() -> new Coupon("테스트 쿠폰", 10500, 5000, Category.FASHION, start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 1,000원 이상 10,000원 이하이며 500원 단위여야 합니다.");
    }

    @Test
    @DisplayName("쿠폰 생성 실패 : 할인 금액이 500원 단위가 아닐 경우")
    void testDiscountAmountNotMultipleOf500() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(5);

        assertThatThrownBy(() -> new Coupon("테스트 쿠폰", 1250, 5000, Category.FASHION, start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 1,000원 이상 10,000원 이하이며 500원 단위여야 합니다.");
    }

    @Test
    @DisplayName("쿠폰 생성 실패 : 할인율이 3% 미만일 경우")
    void testDiscountRateTooLow() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(5);

        assertThatThrownBy(() -> new Coupon("테스트 쿠폰", 1000, 100000, Category.FASHION, start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 3% 이상 20% 이하이어야 합니다.");
    }

    @Test
    @DisplayName("쿠폰 생성 실패 : 할인율이 20% 초과일 경우")
    void testDiscountRateTooHigh() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(5);

        assertThatThrownBy(() -> new Coupon("테스트 쿠폰", 5000, 20000, Category.FASHION, start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 3% 이상 20% 이하이어야 합니다.");
    }

    @Test
    @DisplayName("쿠폰 생성 실패 : 발급 시작일이 종료일 이후일 경우")
    void testIssuancePeriodInvalid() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.minusDays(1);

        assertThatThrownBy(() -> new Coupon("테스트 쿠폰", 1000, 5000, Category.FASHION, start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("발급 시작일은 종료일보다 이전이어야 합니다.");
    }

    @Test
    @DisplayName("쿠폰 생성 실패 : 최소 주문 금액이 5000원 미만일 경우")
    void testMinimumOrderAmountTooLow() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(5);

        assertThatThrownBy(() -> new Coupon("테스트 쿠폰", 1000, 4000, Category.FASHION, start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 5,000원 이상 100,000원 이하여야 합니다.");
    }

    @Test
    @DisplayName("쿠폰 생성 실패 : 최소 주문 금액이 100,000원 초과일 경우")
    void testMinimumOrderAmountTooHigh() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(5);

        assertThatThrownBy(() -> new Coupon("테스트 쿠폰", 1000, 110000, Category.FASHION, start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최소 주문 금액은 5,000원 이상 100,000원 이하여야 합니다.");
    }
}
