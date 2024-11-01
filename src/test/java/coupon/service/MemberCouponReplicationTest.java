package coupon.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.domain.Coupon;
import coupon.domain.CouponRepository;
import coupon.domain.Member;
import coupon.domain.MemberRepository;
import coupon.fixture.CouponFixture;

@SpringBootTest
public class MemberCouponReplicationTest {

    @Autowired
    MemberCouponService memberCouponService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CouponRepository couponRepository;

    @Test
    void 회원의_쿠폰_목록_조회시_복제지연으로_인해_읽기_실패가_발생한다() {
        // given
        Member member = new Member("test");
        Member savedMember = memberRepository.save(member);

        Coupon savedCoupon = couponRepository.save(CouponFixture.create());

        // when
        memberCouponService.addCoupon(savedMember.getId(), savedCoupon.getId());

        // then
        List<Coupon> coupons = memberCouponService.getMemberCoupons(savedMember.getId());
        assertThat(coupons).isEmpty();
    }
}
