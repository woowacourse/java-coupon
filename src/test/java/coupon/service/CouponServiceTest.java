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
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private ReaderService readerService;

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
        Coupon saved = couponService.saveCouponBefore(coupon);
        Thread.sleep(2000);

        // then
        Coupon coupon = readerService.read(() -> couponService.getCoupon(saved.getId()));
        assertThat(coupon).isEqualTo(saved);

        /* log
        1. 쿠폰 저장
        2. Reader DB 접근
        3. 쿠폰 조회 시도
         */
    }

    @Test
    void 방법2_예외_발생시_쓰기DB에서_읽기() {
        // when
        Coupon saved = couponService.saveCouponAfter(coupon);

        // then
        assertThat(saved.getName()).isEqualTo(coupon.getName());

        /* log
        1. 쿠폰 저장
        2. 쿠폰 조회 시도
        3. 쿠폰 조회 중 복제 지연 발생
        4. Reader DB 접근
        5. 쿠폰 조회
         */
    }
}
