package coupon;

import static coupon.domain.Category.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.Coupon;
import coupon.service.CouponQueryService;
import coupon.service.CouponCommandService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponApplicationTests {


    @Autowired
    private CouponCommandService couponCommandService;

    @Autowired
    private CouponQueryService couponQueryService;

    @Test
    void contextLoads() {
    }

    @Test
    void findCouponForPresident() {
        Coupon coupon = couponCommandService.createCoupon(
                new Coupon("쿠폰1", FOOD, 1_000, 10_000, LocalDateTime.now(), LocalDateTime.now().plusDays(1))
        );
        Coupon savedCoupon = couponCommandService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }

    @Test
    void findCouponForCustomer() {
        Coupon coupon = couponCommandService.createCoupon(
                new Coupon("쿠폰1",FOOD, 1_000, 10_000, LocalDateTime.now(), LocalDateTime.now().plusDays(1))
        );
        assertThatThrownBy(()->couponQueryService.getCoupon(coupon.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 쿠폰입니다."); // 지연되도 괜찮다고 판단.
    }

}
