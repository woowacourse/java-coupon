package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class CouponServiceTest {

    @Autowired
    private CouponService couponService;


    @DisplayName("적절한 할인금액으로 쿠폰을 생성한다.")
    @ParameterizedTest
    @CsvSource({
            "1000, 5000",
            "5000, 50000",
            "10000, 100000"
    })
    void couponWithValidDiscountAmount(int discountAmount, int minOrderAmount) {
        // given
        Coupon coupon = new Coupon(
                1L, "Valid Coupon",
                discountAmount, minOrderAmount, Category.ELECTRONICS,
                LocalDateTime.now(), LocalDateTime.now().plusDays(30));

        // when & then
        assertThatCode(() -> couponService.create(coupon)).doesNotThrowAnyException();
    }

    @DisplayName("할인금액이 최소단위가 아닐 시 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "9001, 5000",
            "1001, 10000"
    })
    void exception_When_DiscountAmountIsNotMinUnit(int discountAmount, int minOrderAmount) {
        // given
        Coupon coupon = new Coupon(
                1L, "Invalid Coupon",
                discountAmount, minOrderAmount, Category.ELECTRONICS,
                LocalDateTime.now(), LocalDateTime.now().plusDays(30)
        );

        // when & then
        assertThatThrownBy(() -> couponService.create(coupon))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("원 단위여야 합니다.");
    }


    @DisplayName("할인 금액이 최소 할인금액 미만일 경우 예외가 발생한다.")
    @Test
    void exception_When_DiscountAmountIsLessThanMin() {
        // given
        int discount = 999;
        Coupon coupon = new Coupon(
                1L, "test",
                discount, 1000, Category.ELECTRONICS,
                LocalDateTime.now(), LocalDateTime.now().plusDays(30)
        );

        // when & then
        assertThatThrownBy(() -> couponService.create(coupon))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("할인 금액은")
        ;
    }

    @DisplayName("할인 금액이 최대 할인금액 초과일 경우 예외가 발생한다.")
    @Test
    void exception_When_DiscountAmountIsBiggerThanMax() {
        // given
        int discount = 10001;
        Coupon coupon = new Coupon(
                1L, "test",
                discount, 1000, Category.ELECTRONICS,
                LocalDateTime.now(), LocalDateTime.now().plusDays(30)
        );

        // when & then
        assertThatThrownBy(() -> couponService.create(coupon))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("할인 금액은")
        ;
    }


    @DisplayName("최소 주문 금액이 최소치를 넘지 못하면 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "1000, 4999",   // 최소 주문 금액 미만
            "5000, 3000",   // 최소 할인 금액이 5000원이면서 주문 금액이 3000원인 경우
            "1500, 4500"    // 최소 주문 금액 미만의 유효한 할인 금액
    })
    void exception_When_MinOrderAmountIsLessThanMin(int discountAmount, int minOrderAmount) {
        // given
        Coupon coupon = new Coupon(
                1L, "Invalid Coupon",
                discountAmount, minOrderAmount, Category.ELECTRONICS,
                LocalDateTime.now(), LocalDateTime.now().plusDays(30)
        );

        // when & then
        assertThatThrownBy(() -> couponService.create(coupon))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("최소 주문 금액은");
    }

    @DisplayName("시작일이 종료일보다 이후일 경우 예외가 발생한다.")
    @Test
    void exception_When_StartDateIsAfterEndDate() {
        // given
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.plusDays(1);
        Coupon coupon = new Coupon(
                1L, "test",
                1000, 5000, Category.ELECTRONICS,
                startDate, endDate
        );

        // when & then
        assertThatThrownBy(() -> couponService.create(coupon))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("종료일은 시작일 이후여야 합니다.")
        ;
    }

    @DisplayName("올바른 할인률을 가진 쿠폰을 발행한다.")
    @ParameterizedTest()
    @CsvSource({
            "3000, 100000", // 3%
            "2000, 10000" // 20%
    })
    void testValidateDiscountAmountValid(int discountAmount, int minOrderAmount) {
        // given
        Coupon validCoupon = new Coupon(1L, "Valid Coupon", discountAmount, minOrderAmount, Category.FASHION,
                LocalDateTime.now(), LocalDateTime.now().plusDays(5));

        // when & then
        assertThatCode(() -> couponService.create(validCoupon))
                .doesNotThrowAnyException();
    }

    @DisplayName("할인률이 최대 최소의 범위를 벗어나면 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "2500, 100000", // 2%
            "2500, 10000" // 25%
    })
    void exception_WhenDiscountRateOverLimit(int discountAmount, int minOrderAmount) {
        // given
        Coupon invalidCoupon = new Coupon(2L, "Invalid Coupon", discountAmount, minOrderAmount, Category.FASHION,
                LocalDateTime.now(), LocalDateTime.now().plusDays(5));

        // when & then
        assertThatThrownBy(() -> couponService.create(invalidCoupon))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("할인율은");
    }
}
