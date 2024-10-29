package coupon.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import coupon.fixture.CouponFixture;

@Transactional
@SpringBootTest
class CouponRepositoryTest {

    @Autowired
    CouponRepository couponRepository;

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
