package coupon.coupons.domain;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

class CouponTest {

    @Test
    @DisplayName("모든 값이 유효한 경우 쿠폰 생성에 성공한다")
    void createCoupon() {
        assertThatCode(() -> new Coupon("유효한 쿠폰", 1000, 5000, Category.FASHION.name(), LocalDateTime.now(), LocalDateTime.now()
                .plusDays(1))).doesNotThrowAnyException();
    }
}
