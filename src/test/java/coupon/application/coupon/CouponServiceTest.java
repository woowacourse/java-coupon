package coupon.application.coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.List;
import coupon.DataSourceRoutingSupport;
import coupon.domain.Coupon;
import coupon.domain.CouponCategory;
import coupon.domain.CouponIssuableDuration;
import coupon.domain.CouponName;
import coupon.domain.MemberCoupon;
import coupon.domain.MemberCouponRepository;
import coupon.support.IntegrationTestSupport;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.data.redis.cache.RedisCacheManager;

class CouponServiceTest extends IntegrationTestSupport {

    @Autowired
    private CouponService couponService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private DataSourceRoutingSupport routingSupport;

    @Autowired
    private RedisCacheManager cacheManager;

    @Test
    @DisplayName("존재하지 않는 쿠폰은 발급할 수 없다.")
    void cantIssue() {
        long unknownCouponId = -1L;

        assertThatThrownBy(() -> couponService.issue(1L, unknownCouponId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("존재하지 않는 쿠폰입니다.");
    }

    @Test
    @DisplayName("사용자 쿠폰을 발급한다.")
    void issue() {
        Coupon coupon = createCoupon();
        couponService.create(coupon);
        couponService.issue(1L, coupon.getId());
        entityManager.clear();

        List<MemberCoupon> result = routingSupport.requireNew(() -> memberCouponRepository.findAll());
        assertThat(result).hasSize(1);
    }

    /**
     * 복제 지연 테스트
     */
    @Test
    @DisplayName("쿠폰을 생성할 수 있다.")
    void create() {
        Coupon coupon = createCoupon();

        couponService.create(coupon);

        Coupon savedCoupon = routingSupport.requireNew(() -> couponService.getCoupon(coupon.getId()));
        assertThat(savedCoupon).isNotNull();
    }

    @Test
    @DisplayName("한 명의 회원은 동일한 쿠폰을 사용한 쿠폰을 포함하여 최대 5장까지 발급할 수 있다.")
    void validateIssuable() {
        Long memberId = 1L;
        Coupon coupon1 = createCoupon();
        couponService.create(coupon1);
        Coupon coupon2 = createCoupon();
        couponService.create(coupon2);
        issueCoupons(memberId, coupon1, 5);
        issueCoupons(memberId, coupon2, 1);

        assertThatThrownBy(() -> couponService.issue(1L, coupon1.getId()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("더 이상 해당 쿠폰을 발급할 수 없습니다.");
    }

    @Test
    @DisplayName("캐시가 미스되는 경우 데이터베이스에서 쿠폰을 조회하여 캐시에 적재한다.")
    void readCouponWithCacheMiss() {
        Coupon coupon = createCoupon();
        couponService.create(coupon);
        Long couponId = coupon.getId();

        ValueWrapper empty = getCacheValue(couponId);
        assertThat(empty).isNull();

        couponService.getCoupon(couponId);
        ValueWrapper afterCaching = getCacheValue(couponId);
        assertThat(afterCaching).isNotNull();
    }

    private ValueWrapper getCacheValue(Long couponId) {
        Cache couponCache = cacheManager.getCache("coupon");

        return couponCache.get(couponId);
    }

    private void issueCoupons(Long memberId, Coupon coupon, int times) {
        for (int i = 0; i < times; i++) {
            couponService.issue(memberId, coupon.getId());
        }
    }

    private Coupon createCoupon() {
        LocalDate today = LocalDate.now();
        LocalDate end = today.plusDays(1);

        return new Coupon(
                new CouponName("1,000원 할인"),
                CouponCategory.FOOD,
                new CouponIssuableDuration(today, end),
                "1000",
                "10000"
        );
    }
}
