package coupon.service;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.dto.CouponRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CouponCommandServiceTest {

    @Autowired
    private CouponCommandService couponCommandService;

    @DisplayName("쿠폰 생성 성공")
    @Test
    void coupon_create() {
        //given
        CouponRequest request = new CouponRequest("쿠폰", 5000, 1000,
                Category.FOOD.name(), LocalDateTime.now(), LocalDateTime.now().plusWeeks(1));

        //when
        Long couponId = couponCommandService.create(request);

        //then
        assertThat(couponId).isNotNull();
    }

    @DisplayName("쿠폰 조회 성공")
    @Test
    void coupon_select() {
        //given
        CouponRequest request = new CouponRequest("쿠폰", 5000, 1000,
                Category.FOOD.name(), LocalDateTime.now(), LocalDateTime.now().plusWeeks(1));
        Long couponId = couponCommandService.create(request);

        //when
        Long findCouponId = couponCommandService.getCoupon(couponId).getId();

        //then
        assertThat(couponId).isEqualTo(findCouponId);
    }
}
