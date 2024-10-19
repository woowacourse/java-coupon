package coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CouponServiceTest extends ServiceTestSupports {

    @Autowired
    private CouponService couponService;

    @DisplayName("쿠폰을 생성해서 저장소에 저장한다.")
    @Test
    void create() {
        Coupon coupon = new Coupon("coupon", 1000, Category.APPLIANCES, 10000);
        couponService.create(coupon);

        Coupon findCoupon = couponService.getCoupon(coupon.getId());

        assertThat(findCoupon.getId()).isEqualTo(coupon.getId());
    }

    @DisplayName("쿠폰을 생성해서 저장하면 캐시에도 저장된다.")
    @Test
    void create2() {
        Coupon coupon = new Coupon("coupon", 1000, Category.APPLIANCES, 10000);
        couponService.create(coupon);

        Map<Long, Coupon> cache = couponService.getCache();

        assertThat(cache.isEmpty()).isFalse();
        assertThat(cache.get(coupon.getId())).isNotNull();
    }

    @DisplayName("쿠폰을 조회할 때 캐시와 repository 모두 존재하지 않으면 예외가 발생한다.")
    @Test
    void getCoupon() {
        assertThatCode(() -> couponService.getCoupon(100L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("쿠폰을 조회할 때 캐시에 존재하면 캐시에서만 반환하고 repository에서 조회하지 않는다.")
    @Test
    void getCoupon2() {
        Map<Long, Coupon> cache = couponService.getCache();
        cache.put(100L, new Coupon("coupon", 1000, Category.APPLIANCES, 10000));

        assertThatCode(() -> couponService.getCoupon(100L))
                .doesNotThrowAnyException();
    }

    @Test
    void 복제지연테스트() {
        Coupon coupon = new Coupon("coupon", 1000, Category.APPLIANCES, 10000);
        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
