package coupon.domain;

import static coupon.domain.Category.FOOD;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponTest {

    @DisplayName("쿠폰의 만료날짜가 발급날짜 보다 앞이면 예외가 발생한다.")
    @Test
    void throw_exception_when_end_day_is_smaller_than_start_day() {
        assertThatThrownBy(
                () -> new Coupon("name", FOOD, 1_000, 10_000, LocalDateTime.now().plusDays(1), LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("발급끝시간은 발급 시작시간보다 앞설 수 없습니다.");
    }


    @DisplayName("쿠폰의 할인률이 3퍼미만이면 예외가 발생한다")
    @Test
    void throw_exception_when_discount_rate_is_smaller_than_3percent() {
        assertThatThrownBy(
                () -> new Coupon("name", FOOD, 500, 25_000, LocalDateTime.now().plusDays(1), LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인률은 3퍼이상 20이하 여야합니다.");
    }

    @DisplayName("쿠폰의 할인률이 20퍼초과하면 예외가 발생한다")
    @Test
    void throw_exception_when_discount_rate_is_grater_than_20percent() {
        assertThatThrownBy(
                () -> new Coupon("name", FOOD, 2_500, 10_000, LocalDateTime.now(), LocalDateTime.now().plusDays(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인률은 3퍼이상 20이하 여야합니다.");
    }
}
