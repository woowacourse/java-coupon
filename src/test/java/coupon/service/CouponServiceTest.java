package coupon.service;

import coupon.domain.Category;
import coupon.domain.Coupon;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    private static final LocalDateTime ISSUABLE_FROM = LocalDateTime.of(2024, 10, 1, 0, 0, 0);
    private static final LocalDateTime ISSUABLE_TO = LocalDateTime.of(2024, 10, 30, 23, 59, 59);

    @Test
    void 복제지연테스트() throws InterruptedException {
        Coupon coupon = new Coupon("쿠폰명",1000, 10000, Category.FASHION, ISSUABLE_FROM, ISSUABLE_TO);
        couponService.createCoupon(coupon);
        Thread.sleep(5000L);
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
