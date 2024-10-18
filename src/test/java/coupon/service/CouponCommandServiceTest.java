package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Coupon;
import coupon.dto.SaveCouponRequest;
import coupon.repository.CouponRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponCommandServiceTest {

    @Autowired
    private CouponCommandService couponCommandService;

    @Autowired
    private CouponRepository couponRepository;

    @DisplayName("쿠폰을 저장한다.")
    @Test
    void save() throws InterruptedException {
        long savedCouponId = couponCommandService.save(new SaveCouponRequest(
                "천원 할인 쿠폰",
                1000,
                10000,
                LocalDate.now().minusDays(10),
                LocalDate.now().plusDays(10),
                "FOOD"));

        // TODO: 복제 지연을 해결한다.
        Thread.sleep(2000);

        Coupon foundCoupon = couponRepository.findById(savedCouponId).get();

        assertThat(foundCoupon.getName()).isEqualTo("천원 할인 쿠폰");
    }
}
