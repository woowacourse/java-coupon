package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class CouponTest {

    @Test
    void 할인을_적용한다() {
        Coupon coupon = new Coupon("첫 주문 할인 쿠폰", Category.FOOD, 1_000L, 12_000L,
                List.of(), LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 31));

        long actual = coupon.discount(10_000L);
        assertThat(actual).isEqualTo(9_000L);
    }
}
