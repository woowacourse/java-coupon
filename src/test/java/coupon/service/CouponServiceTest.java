package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void 복제지연테스트() {
        Coupon coupon = new Coupon("우테코치킨 첫주문 할인",
                1_000,
                5_000,
                LocalDate.parse("2024-10-14"),
                LocalDate.parse("2024-10-20"),
                Category.FASHION);

        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
