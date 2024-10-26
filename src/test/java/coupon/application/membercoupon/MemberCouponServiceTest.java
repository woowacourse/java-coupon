package coupon.application.membercoupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponRepository;
import coupon.domain.member.Member;
import coupon.domain.member.MemberRepository;
import coupon.domain.membercoupon.MemberCoupon;
import coupon.domain.membercoupon.MemberCouponRepository;
import coupon.exception.CouponException;
import coupon.exception.ExceptionType;
import coupon.support.AcceptanceTestSupport;
import coupon.support.data.CouponTestData;
import coupon.support.data.MemberCouponTestData;
import coupon.support.data.MemberTestData;
import org.assertj.core.api.Assertions;
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

    @DisplayName("한 회원이 같은 쿠폰을 5장 초과해 발급받을 수 없다.")
    @Test
    void issueMemberCouponOverFive() {
        Member member = memberRepository.save(MemberTestData.defaultMember().build());
        Coupon coupon = couponRepository.save(CouponTestData.defaultCoupon().build());

        for (int i = 0; i < 5; i++) {
            memberCouponRepository.save(createMemberCoupon(member, coupon));
        }

        assertThatThrownBy(() -> memberCouponService.issueMemberCoupon(member.getId(), coupon.getId()))
                .isInstanceOf(CouponException.class)
                .hasMessage(ExceptionType.ISSUE_MAX_COUNT_EXCEED.getMessage());
    }

    private MemberCoupon createMemberCoupon(Member member, Coupon coupon) {
        return MemberCouponTestData.defaultMemberCoupon()
                .withCouponId(coupon.getId())
                .withMember(member)
                .build();
    }
}
