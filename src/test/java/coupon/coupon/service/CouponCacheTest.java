package coupon.coupon.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import coupon.coupon.CouponFixture;
import coupon.coupon.dto.CouponResponse;
import coupon.member.domain.Member;
import coupon.member.domain.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cache.CacheManager;

@DisplayName("쿠폰 캐싱 테스트")
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class CouponCacheTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CacheManager cacheManager;

    private Member issuer = new Member("Libienz");

    @BeforeEach
    void setUpIssuer() {
        final var member = new Member("Libienz");
        issuer = memberRepository.save(member);
    }

    @AfterEach
    void tearDownIssuer() {
        couponService.deleteAll();
        memberRepository.delete(issuer);
    }

    @DisplayName("쿠폰을 조회하는 경우 캐싱 된다")
    @Test
    void cachedWhenRead() {
        final var couponCreateRequest = CouponFixture.TOUROOT_COUPON.getCouponCreateRequest(issuer);
        final var couponCreateResponse = couponService.createCoupon(couponCreateRequest);

        CouponResponse couponReadResponse = couponService.getCouponByAdmin(couponCreateResponse.id());
        CouponResponse cachedResponse = cacheManager.getCache("coupons")
                .get(couponCreateResponse.id(), CouponResponse.class);

        Assertions.assertAll(
                () -> assertThat(cachedResponse).isNotNull(),
                () -> assertThat(cachedResponse).isEqualTo(couponReadResponse)
        );
    }

    @DisplayName("쿠폰을 생성하는 경우 쿠폰은 캐싱되지 않는다.")
    @Test
    void nonCachedWhenWrite() {
        final var couponCreateRequest = CouponFixture.TOUROOT_COUPON.getCouponCreateRequest(issuer);
        final var couponCreateResponse = couponService.createCoupon(couponCreateRequest);

        CouponResponse cachedResponse = cacheManager.getCache("coupons")
                .get(couponCreateResponse.id(), CouponResponse.class);
        assertThat(cachedResponse).isNull();
    }

    @DisplayName("쿠폰을 생성하는 경우 쿠폰은 캐싱되지 않는다.")
    @Test
    void testDeleteAll_EvictsCache() {
        final var couponCreateRequest = CouponFixture.TOUROOT_COUPON.getCouponCreateRequest(issuer);
        final var couponCreateResponse = couponService.createCoupon(couponCreateRequest);

        couponService.getCouponByAdmin(couponCreateResponse.id());
        couponService.deleteAll();

        CouponResponse cachedResponse = cacheManager.getCache("coupons")
                .get(couponCreateResponse.id(), CouponResponse.class);
        assertThat(cachedResponse).isNull();
    }
}
