package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.coupon.ProductCategory;
import coupon.repository.entity.Coupon;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CouponWriterServiceTest {

    @Autowired
    private CouponWriterService couponWriterService;

    @Autowired
    private CouponReaderService couponReaderService;

    private Coupon coupon;

    @BeforeEach
    void setUp() {
        coupon = new Coupon("coupon_name",
                1000,
                10000,
                ProductCategory.패션,
                LocalDateTime.of(2024, 10, 18, 0, 0),
                LocalDateTime.of(2024, 10, 18, 0, 0).plusDays(5));
    }

    @Test
    void 방법1_읽기_시점_지연() throws InterruptedException {
        // when
        Coupon saved = couponWriterService.saveCouponBefore(coupon);
        Thread.sleep(2000);

        // then
        Coupon coupon = couponReaderService.getCoupon(saved.getId());
        assertThat(coupon).isEqualTo(saved);

        /* log
        1. 쿠폰 저장
        2. reader DB 조회
         */
    }

    @Test
    void 방법2_예외_발생시_쓰기DB에서_읽기() {
        // when
        Coupon saved = couponWriterService.saveCouponAfter(coupon);

        // then
        assertThat(saved.getName()).isEqualTo(coupon.getName());

        /* log
        1. 쿠폰 저장 후 조회
        2. reader DB 조회
        3. 저장 중 복제 지연 발생
        4. writer DB 조회
         */
    }
}
