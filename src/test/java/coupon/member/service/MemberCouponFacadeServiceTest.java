package coupon.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.entity.CouponEntity;
import coupon.coupon.dto.request.CouponRequest;
import coupon.coupon.service.CouponService;
import coupon.member.domain.Member;
import coupon.member.domain.MemberCoupon;
import coupon.member.request.MemberCouponRequest;

@SpringBootTest
class MemberCouponFacadeServiceTest {

    @Autowired
    private MemberCouponFacadeService memberCouponFacadeService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private CacheManager cacheManager;

    private Member testMember;

    @BeforeEach
    void setUp() {
        testMember = memberService.create(new Member("test@test.com"));

        // 캐시 초기화
        Cache couponCache = cacheManager.getCache("couponCache");
        if (couponCache != null) {
            couponCache.clear();
        }
    }

    @Test
    @DisplayName("회원 쿠폰 발급 성공")
    void createSuccess() {
        // given
        CouponRequest couponRequest = new CouponRequest(
                "2000원 할인 쿠폰",
                1000,
                30000,
                Category.FOOD,
                LocalDateTime.of(2024, 10, 17, 10, 10),
                LocalDateTime.of(2024, 10, 18, 10, 10)
        );
        CouponEntity testCoupon = couponService.create(couponRequest);
        MemberCouponRequest request = new MemberCouponRequest(testMember.getId(), testCoupon.getId());

        // when
        MemberCoupon memberCoupon = memberCouponFacadeService.create(request);

        // then
        assertThat(memberCoupon.getMember().getId()).isEqualTo(testMember.getId());
        assertThat(memberCoupon.getCouponEntity().getId()).isEqualTo(testCoupon.getId());
    }
}
