package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.coupon.business.CouponService;
import coupon.coupon.common.Fixture;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.IssuePeriod;
import coupon.member.persistence.MemberWriter;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private MemberWriter memberWriter;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void 복제지연테스트() {
        IssuePeriod issuePeriod = new IssuePeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1L));
        Coupon coupon = Fixture.generateBigSaleFashionCoupon(issuePeriod);
        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }

    @DisplayName("쿠폰을 조회할 때 캐시 데이터가 없다면 캐시에 저장하고 다음 조회시 캐시에서 반환한다.")
    @Test
    void findAllByMemberIdFromCache() {
        memberWriter.create(Fixture.generateMember());
        IssuePeriod issuePeriod = new IssuePeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1L));
        Coupon coupon = couponService.create(Fixture.generateBigSaleFashionCoupon(issuePeriod));

        Cache cache = cacheManager.getCache(CouponService.COUPON_CACHE_NAME);
        assertThat(cache.get(coupon.getId())).isNull();

        Coupon findCoupon = couponService.getCoupon(coupon.getId());
        Coupon cachedData = (Coupon) cache.get(coupon.getId()).get();

        assertThat(findCoupon.getId()).isEqualTo(cachedData.getId());
    }
}
