package coupon.memberCoupon.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.coupon.domain.Coupon;
import coupon.member.domain.Member;
import coupon.utils.IsolatedTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MemberCouponServiceTest extends IsolatedTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @DisplayName("동일한 쿠폰을 5장 넘게 발급 시도 시 예외가 발생한다.")
    @Test
    void issueOverMaximumCount() {
        Member member = memberRepository.save(new Member());
        Coupon coupon = couponRepository.save(new Coupon());

        // 5장 발급
        memberCouponService.issueMemberCoupon(member.getId(), coupon.getId());
        memberCouponService.issueMemberCoupon(member.getId(), coupon.getId());
        memberCouponService.issueMemberCoupon(member.getId(), coupon.getId());
        memberCouponService.issueMemberCoupon(member.getId(), coupon.getId());
        memberCouponService.issueMemberCoupon(member.getId(), coupon.getId());

        // 6장째 발급 시도 시 예외 발생
        assertThatThrownBy(() -> memberCouponService.issueMemberCoupon(member.getId(), coupon.getId()))
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasMessage("동일한 쿠폰은 최대 5장 까지만 발급할 수 있습니다.");
    }
}
