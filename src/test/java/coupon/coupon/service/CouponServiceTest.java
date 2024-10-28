package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponName;
import coupon.coupon.domain.DiscountAmount;
import coupon.coupon.domain.IssuablePeriod;
import coupon.coupon.domain.MinimumOrderAmount;
import coupon.coupon.repository.CouponRepository;
import coupon.global.domain.CouponFixture;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
class CouponServiceTest {

    @SpyBean
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    void 복제지연테스트() {
        CouponName couponName = new CouponName("쿠폰");
        DiscountAmount discountAmount = new DiscountAmount(10000);
        MinimumOrderAmount minimumOrderAmount = new MinimumOrderAmount(100000);
        IssuablePeriod issuablePeriod
                = new IssuablePeriod(LocalDate.parse("2024-10-18"), LocalDate.parse("2024-10-18"));

        Coupon coupon = new Coupon(couponName, discountAmount, minimumOrderAmount, Category.FOODS, issuablePeriod);
        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }

    @Test
    @DisplayName("쿠폰 조회 시 캐싱한 후 반환할 수 있다.")
    void should_return_coupon_when_cache_coupon() {
        // given
        Coupon coupon = CouponFixture.createCoupon();

        // when
        couponRepository.save(coupon);

        // then
        couponService.getCoupon(coupon.getId());
        verify(couponService, times(1)).getCoupon(coupon.getId());

        couponService.getCoupon(coupon.getId());
        verify(couponService, times(1)).getCoupon(coupon.getId());
    }
}
