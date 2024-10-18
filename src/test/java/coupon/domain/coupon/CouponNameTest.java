package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class CouponNameTest {

    @DisplayName("유효한 쿠폰 이름을 입력되면 객체를 생성한다.")
    @Test
    void createObject() {
        // Given
        final String input = "싱싱한 켈리 할인 쿠폰";

        // When
        final CouponName couponName = new CouponName(input);

        // Then
        assertThat(couponName).isNotNull();
    }

    @DisplayName("null 혹은 빈 값이 입력되면 예외를 발생시킨다.")
    @NullAndEmptySource
    @ParameterizedTest
    void validateValueIsNullOrBlank(final String input) {
        // When & Then
        assertThatThrownBy(() -> new CouponName(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름은 null 혹은 빈 값을 입력할 수 없습니다.");
    }

    @DisplayName("유효하지 않은 길이의 값이 입력되면 예외를 발생시킨다.")
    @Test
    void validateValueLength() {
        // Given
        final String input = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        // When & Then
        assertThatThrownBy(() -> new CouponName(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름의 길이는 최대 30자 이하여야 합니다. - " + input.length());
    }

    @DisplayName("동일한 쿠폰 이름을 가진 객체와 동등성 비교를 하면 true를 반환한다.")
    @Test
    void equalsIsTrue() {
        // Given
        final CouponName couponNameA = new CouponName("싱싱한 켈리 할인 쿠폰");
        final CouponName couponNameB = new CouponName("싱싱한 켈리 할인 쿠폰");

        // When
        final boolean isEqual = couponNameA.equals(couponNameB);

        // Then
        assertThat(isEqual).isTrue();
    }

    @DisplayName("동일하지 않은 쿠폰 이름을 가진 객체와 동등성 비교를 하면 false를 반환한다.")
    @Test
    void equalsIsFalse() {
        // Given
        final CouponName couponNameA = new CouponName("싱싱한 켈리 할인 쿠폰");
        final CouponName couponNameB = new CouponName("싱싱한 도라 할인 쿠폰");

        // When
        final boolean isEqual = couponNameA.equals(couponNameB);

        // Then
        assertThat(isEqual).isFalse();
    }
}
