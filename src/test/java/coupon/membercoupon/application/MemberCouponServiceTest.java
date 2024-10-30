package coupon.membercoupon.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import coupon.common.infra.datasource.DataSourceHelper;
import coupon.coupon.application.CouponResponse;
import coupon.coupon.application.CouponService;
import coupon.coupon.application.CreateCouponRequest;
import coupon.member.domain.Member;
import coupon.member.domain.MemberRepository;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.domain.MemberCouponRepository;
import coupon.support.IntegrationTestSupport;
import coupon.support.data.MemberCouponTestData;
import coupon.support.data.MemberTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MemberCouponServiceTest extends IntegrationTestSupport {

    @Autowired
    private DataSourceHelper dataSourceHelper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponService couponService;

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private MemberCouponMapper memberCouponMapper;

    @Test
    @DisplayName("멤버 쿠폰과 함께 쿠폰 정보를 조회할 수 있다.")
    void getMemberCouponWithCoupons() {
        // given
        Member member = memberRepository.save(MemberTestData.defaultMember().build());
        CouponResponse couponResponse = create();

        MemberCoupon memberCoupon1 = createMemberCoupon(member.getId(), couponResponse.id());
        MemberCoupon memberCoupon2 = createMemberCoupon(member.getId(), couponResponse.id());

        // when
        List<MemberCouponWithCouponResponse> responses = dataSourceHelper.executeInWriter(
                () -> memberCouponService.getMemberCouponWithCoupons(member.getId())
        );

        // then
        assertThat(responses).containsExactly(
                memberCouponMapper.toWithCouponResponse(memberCoupon1, couponResponse),
                memberCouponMapper.toWithCouponResponse(memberCoupon2, couponResponse)
        );
    }

    private CouponResponse create() {
        CreateCouponRequest request = new CreateCouponRequest(
                "coupon",
                1000L,
                10000L,
                "FOOD",
                LocalDate.now(),
                LocalDate.now()
        );
        return couponService.create(request);
    }

    private MemberCoupon createMemberCoupon(Long memberId, Long couponId) {
        MemberCoupon memberCoupon = MemberCouponTestData.defaultMemberCoupon()
                .withMemberId(memberId)
                .withCouponId(couponId)
                .build();

        return memberCouponRepository.save(memberCoupon);
    }
}
