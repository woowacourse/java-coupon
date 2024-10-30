package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import coupon.cache.CachedCoupon;
import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.vo.DiscountAmount;
import coupon.domain.vo.IssuePeriod;
import coupon.domain.vo.MinimumOrderPrice;
import coupon.domain.vo.Name;
import coupon.repository.CachedCouponRepository;
import coupon.repository.CouponRepository;
import coupon.util.DatabaseCleaner;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CouponCacheManagerTest {

    @SpyBean
    private CouponRepository couponRepository;

    @Autowired
    private CachedCouponRepository cachedCouponRepository;

    @Autowired
    private CouponCacheManager couponCacheManager;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    private Coupon coupon;

    @BeforeEach
    void setUp() {
        databaseCleaner.clear();

        Name name = new Name("쿠폰이름");
        DiscountAmount discountAmount = new DiscountAmount(1_000);
        MinimumOrderPrice minimumOrderPrice = new MinimumOrderPrice(30_000);
        IssuePeriod issuePeriod = new IssuePeriod(LocalDateTime.now(), LocalDateTime.now());
        Coupon coupon = new Coupon(name, discountAmount, minimumOrderPrice, Category.FASHION, issuePeriod);

        this.coupon = couponRepository.save(coupon);
    }

    @Test
    @DisplayName("캐시에 존재하는 쿠폰 정보를 업데이트한다.")
    void update() {
        // given
        cachedCouponRepository.save(new CachedCoupon(coupon));

        Coupon updatedCoupon = new Coupon(coupon.getId(), new Name("수정된 쿠폰이름"), coupon.getDiscountAmount(),
                coupon.getMinimumOrderPrice(), coupon.getCategory(), coupon.getIssuePeriod());

        // when
        couponCacheManager.update(updatedCoupon);

        // then
        CachedCoupon cachedCoupon = cachedCouponRepository.findById(coupon.getId()).get();
        assertThat(cachedCoupon.getCoupon().getName().getValue()).isEqualTo("수정된 쿠폰이름");
    }

    @Test
    @DisplayName("쿠폰 목록을 조회할 때 Look aside 캐시를 사용한다.")
    void findCouponInCache() {
        // given
        couponCacheManager.finAllByIds(Set.of(1l)); // 이미 한 번 조회됨

        // when
        couponCacheManager.finAllByIds(Set.of(1l));

        // then
        verify(couponRepository, times(1)).findAllByIdIn(anySet());
    }
}
