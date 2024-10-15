package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponTest {

    @Test
    @DisplayName("쿠폰이 정상 생성된다.")
    void createCoupon() {
        // when & then
        LocalDate now = LocalDate.now();
        assertThatCode(() -> new Coupon("쿠폰", 1_000, 30_000, CouponCategory.FASHION, now, now))
                .doesNotThrowAnyException();
    }
}
