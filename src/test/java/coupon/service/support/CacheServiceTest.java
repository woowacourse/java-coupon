package coupon.service.support;

import static org.assertj.core.api.Assertions.assertThatCode;

import coupon.domain.Coupon;
import coupon.service.CouponService;
import coupon.support.CacheCleanerExtension;
import coupon.support.DatabaseCleaner;
import coupon.support.DatabaseCleanerExtension;
import coupon.support.Fixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@ExtendWith(value = {DatabaseCleanerExtension.class, CacheCleanerExtension.class})
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class CacheServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @DisplayName("쿠폰이 캐시되었는지 확인한다.")
    @Test
    void getCouponWithCacheTest() throws Exception {
        // given
        Coupon coupon = couponService.create(Fixture.COUPON);
        waitForSeconds(2);
        couponService.getCoupon(coupon.getId());

        databaseCleaner.execute();

        // when, then
        assertThatCode(() -> couponService.getCoupon(coupon.getId()))
                .doesNotThrowAnyException();
    }

    void waitForSeconds(int seconds) throws Exception {
        Thread.sleep(1000L * seconds);
    }
}
