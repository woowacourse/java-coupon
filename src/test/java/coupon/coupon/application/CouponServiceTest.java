package coupon.coupon.application;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.common.infra.datasource.DataSourceHelper;
import coupon.coupon.domain.Coupon;
import coupon.support.data.CouponTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponQueryService couponQueryService;

    @Autowired
    private CouponCommandService couponCommandService;

    @Autowired
    private DataSourceHelper dataSourceHelper;

    @Test
    @DisplayName("복제 지연 테스트")
    void replicationLagTest() {
        // given
        Coupon coupon = CouponTestData.defaultCoupon().build();

        // when
        couponCommandService.create(coupon);
        Coupon savedCoupon = dataSourceHelper.executeInWriter(() -> couponQueryService.getCoupon(coupon.getId()));

        // then
        assertThat(savedCoupon).isNotNull();
    }
}
