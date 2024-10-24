package coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class CouponServiceTest extends ServiceTestSupports {

    @Autowired
    private CouponService couponService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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

        Coupon actual = (Coupon) redisTemplate.opsForValue().get("coupon:" + coupon.getId());

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(coupon.getId());
    }

    @DisplayName("쿠폰을 조회할 때 캐시와 repository 모두 존재하지 않으면 예외가 발생한다.")
    @Test
    void getCoupon() {
        long targetId = 1L;
        redisTemplate.delete("coupon:" + targetId);
        assertThatCode(() -> couponService.getCoupon(targetId))
                .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("쿠폰을 조회할 때 캐시에 존재하면 캐시에서만 반환하고 repository에서 조회하지 않는다.")
    @Test
    void getCoupon2() {
        Coupon coupon = new Coupon("coupon", 1000, Category.APPLIANCES, 10000);
        redisTemplate.opsForValue().set("coupon:" + 100, coupon);

        assertThatCode(() -> couponService.getCoupon(100L))
                .doesNotThrowAnyException();
    }

    @DisplayName("쿠폰을 조회할 때 source DB에 존재하지 않고 캐시에도 존재하지 않으면 replica DB에서 조회 후 캐싱한다.")
    @Test
    void getCoupon3() {
        long id = 1L;
        long startTime = System.currentTimeMillis();
        Coupon coupon = new Coupon("coupon", 1000, Category.APPLIANCES, 10000);
        couponService.create(coupon);
        redisTemplate.delete("coupon:" + id);

        Coupon couponFromReplica = couponService.getCoupon(id);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        int replicaLagMilliseconds = 1000;

        assertThat(duration).isLessThan(replicaLagMilliseconds);
        assertThat(couponFromReplica).isNotNull();
    }

    @Test
    void 복제지연테스트() {
        Coupon coupon = new Coupon("coupon", 1000, Category.APPLIANCES, 10000);
        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
