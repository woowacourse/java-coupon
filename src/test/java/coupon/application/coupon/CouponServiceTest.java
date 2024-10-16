package coupon.application.coupon;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CouponServiceTest {

    @Autowired
    private CouponWriteService couponWriteService;

    @Autowired
    private CouponReadService couponReadService;

    @Test
    @DisplayName("복제 지연 테스트")
    void replicationLag() {
        CouponResponse couponResponseFromWrite = couponWriteService.generateNewCoupon(getCouponGenerateRequest());

        CouponResponse couponResponseFromRead = couponReadService.findCouponById(couponResponseFromWrite.id());

        assertThat(couponResponseFromWrite).isEqualTo(couponResponseFromRead);
    }

    private CouponGenerateRequest getCouponGenerateRequest() {
        return new CouponGenerateRequest(
                "쿠폰",
                1000,
                30000,
                "FASHION",
                "2024-10-01",
                "2024-10-31"
        );
    }
}
