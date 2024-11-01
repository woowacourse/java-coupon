package coupon.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import coupon.fixture.CouponFixture;
import coupon.support.CouponMockRepository;

class CouponRepositoryTest {

    CouponRepository couponRepository = new CouponMockRepository();

    @Test
    void 쿠폰을_저장한다() {
        // given
        Coupon coupon = CouponFixture.create();

        // when
        Coupon savedCoupon = couponRepository.save(coupon);

        // then
        assertThat(savedCoupon.getName()).isEqualTo("테스트 쿠폰");
        assertThat(savedCoupon.getDiscountRate()).isEqualTo(10);
    }
}
