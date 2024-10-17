package coupon.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import coupon.domain.Coupon;
import coupon.domain.CouponCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private DataSourceHelper dataSourceHelper;

    @Test
    @DisplayName("복제 지연 테스트")
    void replicationLazyTest() {
        // given
        LocalDate now = LocalDate.now();
        Coupon coupon = new Coupon("쿠폰", 1_000, 30_000, CouponCategory.FASHION, now, now);

        // when
        couponService.create(coupon);
        Coupon savedCoupon = dataSourceHelper.executeInWriter(() -> couponService.getCoupon(coupon.getId()));

        // then
        assertThat(savedCoupon).isNotNull();
    }
}
