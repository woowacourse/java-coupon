package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponTest {

    @Test
    @DisplayName("할인율이 3%이상 20%이하가 아니라면 예외가 발생한다.")
    void invalid_discount_rate() {
        //given
        final CouponName name = new CouponName("오다 주웠다");
        final DiscountAmount discountAmount = new DiscountAmount(10000);
        final MinOrderAmount minOrderAmount = new MinOrderAmount(10000);
        final IssuancePeriod issuancePeriod = new IssuancePeriod(LocalDate.now(), LocalDate.now().plusDays(10));
        final Coupon coupon = new Coupon(name, discountAmount, minOrderAmount, issuancePeriod);

        //when && then
        assertThatThrownBy(coupon::calculateDiscountRate)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("할인율을 계산한다.")
    void calculate_rate() {
        //given
        final CouponName name = new CouponName("무..물고기");
        final DiscountAmount discountAmount = new DiscountAmount(4000);
        final MinOrderAmount minOrderAmount = new MinOrderAmount(20000);
        final IssuancePeriod issuancePeriod = new IssuancePeriod(LocalDate.now(), LocalDate.now().plusDays(10));
        final Coupon coupon = new Coupon(name, discountAmount, minOrderAmount, issuancePeriod);

        //when
        final double calculateDiscountRate = coupon.calculateDiscountRate();

        //then
        assertThat(calculateDiscountRate).isEqualTo(20.0);
    }
}
