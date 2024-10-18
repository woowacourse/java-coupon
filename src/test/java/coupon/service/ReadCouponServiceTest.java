package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.coupon.Coupon;
import coupon.support.CouponFixtureGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReadCouponServiceTest {

    @Autowired
    private ReadCouponService readCouponService;

    @Autowired
    private WriteCouponService writeCouponService;

    @DisplayName("복제 지연으로 인한 이슈를 해결한다.")
    @Test
    void resolveReplicationLag() {
        Coupon coupon = CouponFixtureGenerator.generate(1_000, 10_000);
        writeCouponService.save(coupon);
        Coupon savedCoupon = readCouponService.findById(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
