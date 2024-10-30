package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.cache.CachedCoupon;
import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.vo.DiscountAmount;
import coupon.domain.vo.IssuePeriod;
import coupon.domain.vo.MinimumOrderPrice;
import coupon.domain.vo.Name;
import coupon.repository.CachedCouponRepository;
import coupon.repository.CouponRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CachedCouponRepository cachedCouponRepository;

    @Autowired
    private CouponRepository couponRepository;

    private Coupon coupon;

    @BeforeEach
    void setUp() {
        Name name = new Name("쿠폰이름");
        DiscountAmount discountAmount = new DiscountAmount(1_000);
        MinimumOrderPrice minimumOrderPrice = new MinimumOrderPrice(30_000);
        IssuePeriod issuePeriod = new IssuePeriod(LocalDateTime.now(), LocalDateTime.now());
        this.coupon = new Coupon(name, discountAmount, minimumOrderPrice, Category.FASHION, issuePeriod);
    }

    @Test
    @DisplayName("복제 지연으로 쿠폰이 조회되지 않는 현상을 방지한다.")
    void occurReplicationLag() {
        // when
        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCouponInReplicationLag(coupon.getId());

        // then
        assertThat(savedCoupon).isNotNull();
    }

    @Test
    @DisplayName("쿠폰 정보를 업데이트할 때 캐시에 쿠폰이 있다면 캐시 정보를 업데이트한다.")
    void updateCouponInCache() {
        // given
        Coupon createdCoupon = couponRepository.save(coupon);
        cachedCouponRepository.save(new CachedCoupon(createdCoupon));

        Coupon updatedCoupon = new Coupon(createdCoupon.getId(), new Name("수정된 쿠폰이름"),
                createdCoupon.getDiscountAmount(), createdCoupon.getMinimumOrderPrice(), createdCoupon.getCategory(),
                createdCoupon.getIssuePeriod());

        // when
        couponService.update(updatedCoupon);

        // then
        CachedCoupon cachedCoupon = cachedCouponRepository.findById(createdCoupon.getId()).get();
        assertThat(cachedCoupon.getCoupon().getName().getValue()).isEqualTo("수정된 쿠폰이름");
    }
}
