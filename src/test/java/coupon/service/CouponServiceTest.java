package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.config.DataSourceHelper;
import coupon.domain.Coupon;
import coupon.support.CacheCleanerExtension;
import coupon.support.DatabaseCleanerExtension;
import coupon.support.Fixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@ExtendWith(value = {DatabaseCleanerExtension.class, CacheCleanerExtension.class})
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class CouponServiceTest {

    @Autowired
    private DataSourceHelper dataSourceHelper;

    @Autowired
    private CouponService couponService;

    @Test
    void 복제_지연_테스트() {
        Coupon coupon = couponService.create(Fixture.COUPON);
        Coupon savedCoupon = dataSourceHelper.executeOnWrite(() -> couponService.getCoupon(coupon.getId()));
        assertThat(savedCoupon).isNotNull();
    }
}
