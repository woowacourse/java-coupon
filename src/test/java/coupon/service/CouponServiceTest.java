package coupon.service;

import static org.assertj.core.api.Assertions.assertThatCode;

import coupon.domain.Coupon;
import coupon.dto.request.CouponSaveRequest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void 복제지연테스트() {
        CouponSaveRequest request = new CouponSaveRequest("행운쿠폰", 1000L, 30000L, "패션", LocalDateTime.now(),
                LocalDateTime.now().plusDays(1));
        Coupon saved = couponService.save(request);

        assertThatCode(() -> couponService.findById(saved.getId()))
                .doesNotThrowAnyException();
    }
}
