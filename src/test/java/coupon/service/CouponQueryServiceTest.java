package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponQueryServiceTest {

    @Autowired
    private CouponQueryService couponQueryService;

    @Autowired
    private CouponRepository couponRepository;

    @DisplayName("성공: 존재하는 ID로 조회")
    @Test
    void findById() throws InterruptedException {
        Coupon saved = couponRepository.save(new Coupon("천원 할인 쿠폰", 1000, 10000,
                LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), "FOOD"));

        // TODO: 복제 지연을 해결한다.
        Thread.sleep(2000);

        Coupon found = couponQueryService.findById(saved.getId());

        assertThat(saved.getId()).isEqualTo(found.getId());
    }
}
