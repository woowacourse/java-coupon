package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("쿠폰 테스트")
class CouponTest {

    @DisplayName("적절한 이름으로 쿠폰을 생성한다.")
    @ValueSource(strings = {"c", "FreeCoupon", "This string contains thirty."})
    @ParameterizedTest
    void couponWithValidName(String name) {
        // when & then
        assertThatCode(
                () -> new Coupon(
                        1L, name,
                        1000, 5000, Category.ELECTRONICS, LocalDateTime.now(),
                        LocalDateTime.now().plusDays(30)
                )
        ).doesNotThrowAnyException();

    }

    @DisplayName("이름이 존재하지 않으면 예외가 발생한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void exception_WhenNameIsNotExist(String name) {
        // when & then
        assertThatThrownBy(
                () -> new Coupon(
                        1L, name,
                        1000, 1000, Category.ELECTRONICS,
                        LocalDateTime.now(), LocalDateTime.now().plusDays(30)
                )
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이름이 최대글자 초과시 예외가 발생한다.")
    @Test
    void exception_When_NameIsOverMaxLenght() {
        // given
        String tooLongName = "a".repeat(31);

        // when & then
        assertThatThrownBy(
                () -> new Coupon(
                        1L, tooLongName,
                        1000, 1000, Category.ELECTRONICS,
                        LocalDateTime.now(), LocalDateTime.now().plusDays(30)
                )
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("할인율을 계산한다.")
    @Test
    void calculateDiscountRate() {
        // given
        Coupon coupon = new Coupon(
                1L, "test",
                1000, 5000, Category.ELECTRONICS, // (1000*100 / 5000) = 20%
                LocalDateTime.now(), LocalDateTime.now().plusDays(30)
        );

        // when
        double discountRate = coupon.calculateDiscountRate();

        // then
        assertThat(discountRate).isEqualTo(20);
    }
}
