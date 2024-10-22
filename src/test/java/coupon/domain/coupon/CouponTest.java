package coupon.domain.coupon;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponTest {

    @DisplayName("같은 값의 CouponName을 가지고 있는 쿠폰 객체들을 동등성 비교 하면 true를 반환한다.")
    @Test
    void equalsIsTrue() {
        // Given
        final Coupon couponA = Coupon.create(
                "싱싱한 켈리 할인 쿠폰",
                10000,
                1000,
                ProductionCategory.FOOD,
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(3)
        );
        final Coupon couponB = Coupon.create(
                "싱싱한 켈리 할인 쿠폰",
                20000,
                3000,
                ProductionCategory.FASHION,
                LocalDateTime.now().plusDays(4),
                LocalDateTime.now().plusDays(8)
        );

        // When
        final boolean equals = couponA.equals(couponB);

        // Then
        assertThat(equals).isTrue();
    }

    @DisplayName("다른 값의 CouponName을 가지고 있는 쿠폰 객체들을 동등성 비교 하면 false를 반환한다.")
    @Test
    void equalsIsFalse() {
        // Given
        final Coupon couponA = Coupon.create(
                "싱싱한 켈리 할인 쿠폰",
                10000,
                1000,
                ProductionCategory.FOOD,
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(3)
        );
        final Coupon couponB = Coupon.create(
                "싱싱한 도라 할인 쿠폰",
                10000,
                1000,
                ProductionCategory.FOOD,
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(3)
        );

        // When
        final boolean equals = couponA.equals(couponB);

        // Then
        assertThat(equals).isFalse();
    }
}
