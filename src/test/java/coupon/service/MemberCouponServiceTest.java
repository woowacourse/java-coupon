package coupon.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import coupon.domain.Coupon;
import coupon.domain.CouponRepository;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.domain.MemberCouponRepository;
import coupon.domain.MemberRepository;
import coupon.fixture.CouponFixture;
import coupon.support.CouponMockRepository;
import coupon.support.MemberCouponMockRepository;
import coupon.support.MemberMockRepository;

public class MemberCouponServiceTest {

    MemberCouponService memberCouponService;
    MemberRepository memberRepository;
    CouponRepository couponRepository;
    MemberCouponRepository memberCouponRepository;

    @BeforeEach
    void setUp() {
        memberRepository = new MemberMockRepository();
        couponRepository = new CouponMockRepository();
        memberCouponRepository = new MemberCouponMockRepository();
        memberCouponService = new MemberCouponService(memberCouponRepository, couponRepository);
    }

    @Test
    void 회원에게_쿠폰을_발급한다() {
        // given
        Member member = new Member("test");
        Member savedMember = memberRepository.save(member);

        Coupon savedCoupon = couponRepository.save(CouponFixture.create());

        // when
        MemberCoupon memberCoupon = memberCouponService.addCoupon(savedMember.getId(), savedCoupon.getId());

        // then
        assertThat(memberCoupon.getMemberId()).isEqualTo(savedMember.getId());
        assertThat(memberCoupon.getCouponId()).isEqualTo(savedCoupon.getId());
    }

    @Test
    void 한_명의_회원은_동일한_쿠폰을_사용한_쿠폰을_포함하여_최대_5장까지_발급할_수_있다() {
        // given
        Member member = new Member("test");
        Member savedMember = memberRepository.save(member);

        Coupon savedCoupon = couponRepository.save(CouponFixture.create());

        // 4개의 동일한 쿠폰을 미리 발급
        for (int i = 0; i < 4; i++) {
            memberCouponService.addCoupon(savedMember.getId(), savedCoupon.getId());
        }

        // when
        MemberCoupon fifthCoupon = memberCouponService.addCoupon(savedMember.getId(), savedCoupon.getId());

        // then
        assertThat(fifthCoupon.getMemberId()).isEqualTo(savedMember.getId());
        assertThat(fifthCoupon.getCouponId()).isEqualTo(savedCoupon.getId());

        assertThatThrownBy(() -> memberCouponService.addCoupon(savedMember.getId(), savedCoupon.getId()))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 회원의_쿠폰_목록을_조회한다() {
        // given
        Member member = new Member("test");
        Member savedMember = memberRepository.save(member);

        Coupon savedCoupon = couponRepository.save(CouponFixture.create());

        // when
        memberCouponService.addCoupon(savedMember.getId(), savedCoupon.getId());

        // then
        List<Coupon> coupons = memberCouponService.getMemberCoupons(savedMember.getId());
        assertThat(coupons).isNotEmpty();
    }
}
