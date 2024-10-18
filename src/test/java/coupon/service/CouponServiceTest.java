package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import coupon.entity.CouponEntity;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    @DisplayName("쿠폰 생성 테스트")
    void createCoupon() {
        Coupon coupon = new Coupon(null, "hello, coupon!", 1000,
                10000, Category.FOOD, LocalDate.now(), LocalDate.now().plusDays(7));
        couponService.create(coupon);
    }

    @Test
    @DisplayName("복제지연 테스트")
    void duplicationDelay() {
        Coupon coupon = new Coupon(null, "coupon!!", 1000,
                10000, Category.FOOD, LocalDate.now(), LocalDate.now().plusDays(7));
        CouponEntity couponEntity = couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(couponEntity.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
