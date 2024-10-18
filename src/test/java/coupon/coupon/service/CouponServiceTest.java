package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.coupon.domain.CouponCategory;
import coupon.coupon.domain.CouponEntity;
import coupon.coupon.repository.CouponRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @Transactional
    @Test
    void 쿠폰을_생성한다() {
        // given
        String couponName = "couponName";
        LocalDateTime couponStartAt = LocalDateTime.of(1, 1, 1, 1, 1);

        // when
        long couponId = couponService.createCoupon(couponName, 5000, 50000, CouponCategory.FOOD,
                couponStartAt, couponStartAt.plusDays(1));

        // then
        CouponEntity actual = couponRepository.findById(couponId).get();
        assertThat(actual.getCouponName()).isEqualTo(couponName);
    }
}
