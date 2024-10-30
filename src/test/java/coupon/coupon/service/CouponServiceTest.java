package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponEntity;
import coupon.coupon.repository.CouponRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponFailCreator couponFailCreator;

    @DisplayName("복제 지연에도 등록한 쿠폰이 잘 조회되어야 한다.")
    @Test
    void replication_lack_test() {
        Coupon coupon = new Coupon(1000L, 10000L);
        Long id = couponService.create(coupon);
        CouponEntity savedCoupon = couponService.getCoupon(id);
        assertThat(savedCoupon).isNotNull();
    }
}
