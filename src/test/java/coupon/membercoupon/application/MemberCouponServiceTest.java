package coupon.membercoupon.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import coupon.coupon.application.CouponMapper;
import coupon.coupon.application.CouponResponse;
import coupon.coupon.application.CouponService;
import coupon.coupon.application.CreateCouponRequest;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponRepository;
import coupon.member.domain.Member;
import coupon.member.domain.MemberRepository;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.domain.MemberCouponRepository;
import coupon.support.IntegrationTestSupport;
import coupon.support.data.CouponTestData;
import coupon.support.data.MemberCouponTestData;
import coupon.support.data.MemberTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

class MemberCouponServiceTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private MemberCouponMapper memberCouponMapper;

    @Autowired
    private RedisTemplate<?, ?> redisTemplate;

    @Test
    @DisplayName("멤버 쿠폰 조회 테스트")
    void getMemberCouponWithCoupons() throws InterruptedException {
        // given
        Member member = memberRepository.save(MemberTestData.defaultMember().build());
        Coupon notCachedCoupon = couponRepository.save(CouponTestData.defaultCoupon().build());
        CouponResponse cachedCoupon = createCoupon();

        MemberCoupon memberCoupon1 = createMemberCoupon(member.getId(), notCachedCoupon.getId());
        MemberCoupon memberCoupon2 = createMemberCoupon(member.getId(), notCachedCoupon.getId());
        MemberCoupon memberCoupon3 = createMemberCoupon(member.getId(), cachedCoupon.id());
        MemberCoupon memberCoupon4 = createMemberCoupon(member.getId(), cachedCoupon.id());

        Thread.sleep(3000);

        // when
        List<MemberCouponWithCouponResponse> responses = memberCouponService.getMemberCouponWithCoupons(member.getId());

        // then
        assertThat(responses).containsExactly(
                memberCouponMapper.toWithCouponResponse(memberCoupon1, couponMapper.toResponse(notCachedCoupon)),
                memberCouponMapper.toWithCouponResponse(memberCoupon2, couponMapper.toResponse(notCachedCoupon)),
                memberCouponMapper.toWithCouponResponse(memberCoupon3, cachedCoupon),
                memberCouponMapper.toWithCouponResponse(memberCoupon4, cachedCoupon)
        );
    }

    private CouponResponse createCoupon() {
        CreateCouponRequest request = new CreateCouponRequest(
                "coupon",
                1000L,
                10000L,
                "FOOD",
                LocalDate.now(),
                LocalDate.now()
        );
        return couponService.createCoupon(request);
    }

    private MemberCoupon createMemberCoupon(Long memberId, Long couponId) {
        MemberCoupon memberCoupon = MemberCouponTestData.defaultMemberCoupon()
                .withMemberId(memberId)
                .withCouponId(couponId)
                .build();

        return memberCouponRepository.save(memberCoupon);
    }
}
