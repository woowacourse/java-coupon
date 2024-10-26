package coupon.application.membercoupon;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponRepository;
import coupon.domain.member.Member;
import coupon.domain.member.MemberRepository;
import coupon.domain.membercoupon.MemberCoupon;
import coupon.domain.membercoupon.MemberCouponRepository;
import coupon.support.AcceptanceTestSupport;
import coupon.support.data.CouponTestData;
import coupon.support.data.MemberCouponTestData;
import coupon.support.data.MemberTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MemberCouponServiceTest extends AcceptanceTestSupport {

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("특정 멤버의 쿠폰 목록을 조회한다.")
    @Test
    void getMemberCoupons() {
        Member member = memberRepository.save(MemberTestData.defaultMember().build());
        Member otherMember = memberRepository.save(MemberTestData.defaultMember().build());
        Coupon coupon1 = couponRepository.save(CouponTestData.defaultCoupon().build());
        Coupon coupon2 = couponRepository.save(CouponTestData.defaultCoupon().build());
        MemberCoupon memberCoupon1 = createMemberCoupon(member, coupon1);
        MemberCoupon memberCoupon2 = createMemberCoupon(member, coupon2);
        MemberCoupon otherMemberCoupon = createMemberCoupon(otherMember, coupon1);
        memberCouponRepository.saveAll(List.of(memberCoupon1, memberCoupon2, otherMemberCoupon));

        List<MemberCouponResponse> response = memberCouponService.getMemberCoupons(member.getId());


        assertThat(response)
                .map(MemberCouponResponse::id)
                .containsExactly(memberCoupon1.getId(), memberCoupon2.getId())
                .doesNotContain(otherMemberCoupon.getId());
    }

    private MemberCoupon createMemberCoupon(Member member, Coupon coupon) {
        return MemberCouponTestData.defaultMemberCoupon()
                .withCouponId(coupon.getId())
                .withMember(member)
                .build();
    }
}
