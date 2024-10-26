package coupon.membercoupon.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDate;
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

class MemberCouponIssuerTest extends IntegrationTestSupport {

    @Autowired
    private DataSourceHelper dataSourceHelper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponService couponService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private MemberCouponIssuer memberCouponIssuer;

    @Test
    @DisplayName("멤버는 쿠폰에 대한 회원 쿠폰을 발급할 수 있다.")
    void issue() {
        // given
        Member member = memberRepository.save(MemberTestData.defaultMember().build());
        CouponResponse couponResponse = createCoupon();

        // when
        Long memberCouponId = memberCouponIssuer.issue(member.getId(), couponResponse.id());


        // then
        MemberCoupon memberCoupon = dataSourceHelper.executeInWriter(
                () -> memberCouponRepository.findById(memberCouponId).orElseThrow()
        );
        assertThat(memberCoupon.getMemberId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("멤버는 쿠폰에 대한 회원 쿠폰을 최대 5개까지 발급할 수 있다.")
    void issueExceedLimit() {
        // given
        Member member = memberRepository.save(MemberTestData.defaultMember().build());
        CouponResponse couponResponse = createCoupon();
        int maxMemberCouponCount = 5;

        for (int i = 0; i < maxMemberCouponCount; i++) {
            memberCouponIssuer.issue(member.getId(), couponResponse.id());
        }

        // when & then
        assertThatThrownBy(() -> memberCouponIssuer.issue(member.getId(), couponResponse.id()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("회원 쿠폰 발급 한도 초과 5/5");
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
