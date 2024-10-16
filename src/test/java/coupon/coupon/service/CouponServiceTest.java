package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @DisplayName("적절한 할인금액으로 쿠폰을 생성한다.")
    @ValueSource(ints = {1000, 1500, 10000})
    @ParameterizedTest
    void couponWithValidDiscountAmount(int discountAmount) {
        // given
        Coupon coupon = new Coupon(
                1L, "name",
                discountAmount, 5000, Category.ELECTRONICS,
                LocalDateTime.now(), LocalDateTime.now().plusDays(30));

        // when & then
        assertThatCode(() -> couponService.create(coupon)).doesNotThrowAnyException();
    }

    @DisplayName("할인금액이 최소단위가 아닐 시 예외가 발생한다.")
    @ValueSource(ints = {501, 1001, 1001})
    @ParameterizedTest
    void exception_When_DiscountAmountIsNotMinUnit(int discountAmount) {
        // given
        Coupon coupon = new Coupon(
                1L, "test",
                discountAmount, 1000, Category.ELECTRONICS,
                LocalDateTime.now(), LocalDateTime.now().plusDays(30)
        );

        // when & then
        assertThatThrownBy(() -> couponService.create(coupon)).isInstanceOf(IllegalArgumentException.class);
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
        assertThatThrownBy(() -> couponService.create(coupon)).isInstanceOf(IllegalArgumentException.class);
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
        assertThatThrownBy(() -> couponService.create(coupon)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("최소 주문 금액이 최소치를 넘지 못하면 예외가 발생한다.")
    @ValueSource(ints = {500, 4500, 3000})
    @ParameterizedTest
    void exception_When_MinOrderAmountIsLessThanMin(int minOrderAmount) {
        // given
        Coupon coupon = new Coupon(
                1L, "test",
                1000, minOrderAmount, Category.ELECTRONICS,
                LocalDateTime.now(), LocalDateTime.now().plusDays(30)
        );

        // when & then
        assertThatThrownBy(() -> couponService.create(coupon)).isInstanceOf(IllegalArgumentException.class);
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
        assertThatThrownBy(() -> couponService.create(coupon)).isInstanceOf(IllegalArgumentException.class);
    }
}
