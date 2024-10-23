package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import coupon.coupon.controller.response.CouponResponse;
import coupon.coupon.controller.response.CouponsResponse;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.MemberCoupon;
import coupon.coupon.domain.repository.CouponRepository;
import coupon.coupon.domain.repository.MemberCouponRepository;
import coupon.coupon.exception.CouponNotFoundException;
import coupon.member.domain.Member;
import coupon.member.domain.repository.MemberRepository;
import coupon.member.exception.MemberNotFoundException;
import coupon.util.CouponFixture;
import coupon.util.MemberFixture;
import java.util.List;
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

    @DisplayName("존재하지 않는 쿠폰 ID로 발급 요청 시 예외가 발생한다.")
    @Test
    void throwExceptionWhenCouponNotFound() {
        // given
        Member member = memberRepository.save(MemberFixture.createChocochip());
        long couponId = 999L;

        // when & then
        assertThatThrownBy(() -> memberCouponService.issueCoupon(member.getId(), couponId))
                .isInstanceOf(CouponNotFoundException.class)
                .hasMessageContaining(String.valueOf(couponId));
    }

    @DisplayName("존재하지 않는 회원 ID로 발급 요청 시 예외가 발생한다.")
    @Test
    void throwExceptionWhenMemberNotFound() {
        // given
        long memberId = 999L;
        Coupon coupon = couponRepository.save(CouponFixture.createValidFoodCoupon());

        // when & then
        assertThatThrownBy(() -> memberCouponService.issueCoupon(memberId, coupon.getId()))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessageContaining(String.valueOf(memberId));
    }

    @DisplayName("정상적으로 쿠폰 발급 시 회원에게 쿠폰이 발급된다.")
    @Test
    void issueCouponSuccessfully() {
        // given
        Member member = memberRepository.save(MemberFixture.createChocochip());
        Coupon coupon = couponRepository.save(CouponFixture.createValidFoodCoupon());

        // when
        memberCouponService.issueCoupon(member.getId(), coupon.getId());

        // then
        MemberCoupon memberCoupon = memberCouponRepository.findByMemberAndCoupon(member, coupon).orElseThrow();
        assertEquals(member.getId(), memberCoupon.getMember().getId());
        assertEquals(coupon.getId(), memberCoupon.getCoupon().getId());
    }

    @DisplayName("회원이 보유한 쿠폰 목록을 정상적으로 조회한다.")
    @Test
    void getCoupons() {
        // given
        Member member = memberRepository.save(MemberFixture.createChocochip());
        Coupon coupon1 = couponRepository.save(CouponFixture.createValidFoodCoupon());
        Coupon coupon2 = couponRepository.save(CouponFixture.createValidFashionCoupon());

        memberCouponRepository.save(new MemberCoupon(member, coupon1));
        memberCouponRepository.save(new MemberCoupon(member, coupon2));

        // when
        CouponsResponse response = memberCouponService.getCoupons(member.getId());

        // then
        List<CouponResponse> couponResponses = response.coupons();
        assertThat(couponResponses).hasSize(2);

        assertThat(couponResponses)
                .extracting(CouponResponse::couponId)
                .containsExactlyInAnyOrder(coupon1.getId(), coupon2.getId());
    }

    @DisplayName("존재하지 않는 회원 ID로 쿠폰 목록 조회 시 예외가 발생한다.")
    @Test
    void throwExceptionWhenMemberNotFoundInGetCoupons() {
        // given
        long invalidMemberId = 999L;

        // when & then
        assertThatThrownBy(() -> memberCouponService.getCoupons(invalidMemberId))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessageContaining(String.valueOf(invalidMemberId));
    }
}
