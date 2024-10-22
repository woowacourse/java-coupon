package coupon.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import coupon.domain.Coupon;
import coupon.domain.CouponCache;
import coupon.dto.CouponResponse;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponCache couponCache;

    @Test
    void 복제지연테스트() throws Exception {
        Coupon coupon = new Coupon("CouponName", 1_000, 10_000, "가구", LocalDate.now(), LocalDate.now());
        couponService.create(coupon);
        CouponResponse savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }

    @DisplayName("Writer DB에도 없으면 지정한 예외를 반환한다.")
    @Test
    void 복제지연테스트_예외() throws Exception {
        assertThatThrownBy(() -> couponService.getCoupon(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰이 존재하지 않습니다.");
    }

    @DisplayName("쿠폰을 생성할 때 캐시에도 저장한다.")
    @Test
    void saveCache() {
        Coupon coupon = new Coupon("CouponName", 1_000, 10_000, "가구", LocalDate.now(), LocalDate.now());
        Long id = couponService.create(coupon);

        Coupon cacheCoupon = couponCache.findById(id).get();

        assertAll(
                () -> assertThat(cacheCoupon.getId()).isEqualTo(id),
                () -> assertThat(cacheCoupon.getCategory()).isEqualTo(coupon.getCategory()),
                () -> assertThat(cacheCoupon.getDiscountAmount()).isEqualTo(coupon.getDiscountAmount()),
                () -> assertThat(cacheCoupon.getMinOrderAmount()).isEqualTo(coupon.getMinOrderAmount()),
                () -> assertThat(cacheCoupon.getName()).isEqualTo(coupon.getName()),
                () -> assertThat(cacheCoupon.getStartDate()).isEqualTo(coupon.getStartDate()),
                () -> assertThat(cacheCoupon.getEndDate()).isEqualTo(coupon.getEndDate())
        );
    }
}
