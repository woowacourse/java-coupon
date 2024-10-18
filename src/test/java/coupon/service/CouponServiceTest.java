package coupon.service;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.dto.CouponRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void coupon_replica_delay() {
        //given
        CouponRequest request = new CouponRequest("쿠폰", 5000, 1000,
                Category.FOOD.name(), LocalDateTime.now(), LocalDateTime.now().plusWeeks(1));

        //when
        Long couponId = couponService.create(request);
        Coupon savedCoupon = couponService.getCoupon(couponId);

        //then
        assertThat(savedCoupon).isNotNull();
    }
}
