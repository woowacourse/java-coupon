package coupon.memberCoupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponRepository;
import coupon.member.domain.Member;
import coupon.member.repository.MemberRepository;
import coupon.memberCoupon.repository.MemberCouponRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @DisplayName("쿠폰 발급 이후에 DB에서 조회할 수 있다.")
    @Test
    void issueCoupon() {
        Coupon coupon = couponRepository.save(new Coupon());
        Member member = memberRepository.save(new Member());

        Long memberCouponId = memberCouponService.issueMemberCoupon(coupon.getId(), member.getId());

        assertThat(memberCouponRepository.findById(memberCouponId).isPresent()).isTrue();
    }
}
