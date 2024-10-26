package coupon.coupon.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import coupon.common.infra.datasource.DataSourceHelper;
import coupon.support.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CouponServiceTest extends IntegrationTestSupport {

    @Autowired
    private CouponService couponService;

    @Autowired
    private DataSourceHelper dataSourceHelper;

    @Test
    @DisplayName("복제 지연 테스트")
    void replicationLagTest() {
        // given
        CreateCouponRequest request = new CreateCouponRequest(
                "coupon",
                1000L,
                10000L,
                "FOOD",
                LocalDate.now(),
                LocalDate.now()
        );

        // when
        CouponResponse couponResponse = couponService.create(request);
        CouponResponse response = dataSourceHelper.executeInWriter(() -> couponService.getCoupon(couponResponse.id()));

        // then
        assertThat(response).isNotNull();
    }
}
