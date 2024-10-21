package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import coupon.support.data.CouponTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponTest {

    @Test
    @DisplayName("쿠폰이 정상 생성된다.")
    void createCoupon() {
        // when & then
        assertThatCode(() -> CouponTestData.defaultCoupon().build())
                .doesNotThrowAnyException();
    }
}
