package coupon.membercoupon.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponRepository;
import coupon.global.domain.CouponFixture;
import coupon.global.domain.MemberFixture;
import coupon.member.domain.Member;
import coupon.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberCouponServiceTest {

    private static final int MAX_ISSUE_COUNT = 5;

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    @DisplayName("존재하지 않는 쿠폰을 발행하면 예외가 발생한다.")
    void should_throw_exception_when_coupon_not_exists() {
        // given
        Long invalidCouponId = 0L;
        Member member = MemberFixture.createMember();
        memberRepository.save(member);

        // when & then
        assertThatThrownBy(() -> memberCouponService.create(invalidCouponId, member.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 쿠폰입니다. 쿠폰 ID : " + invalidCouponId);
    }

    @Test
    @DisplayName("존재하지 않는 회원이 쿠폰을 발행하면 예외가 발생한다.")
    void should_throw_exception_when_member_not_exists() {
        // given
        Coupon coupon = CouponFixture.createCoupon();
        Long invalidMemberId = 0L;
        couponRepository.save(coupon);

        // when & then
        assertThatThrownBy(() -> memberCouponService.create(coupon.getId(), invalidMemberId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @Test
    @DisplayName("동일한 쿠폰을 발행한 횟수가 5회 미만이면 동일 쿠폰 발행이 가능하다.")
    void should_create_coupon_when_created_same_coupon_under_5_times() {
        // given
        Coupon coupon = CouponFixture.createCoupon();
        couponRepository.save(coupon);
        Member member = MemberFixture.createMember();
        memberRepository.save(member);

        // when
        for (int i = 0; i < 4; i++) {
            memberCouponService.create(coupon.getId(), member.getId());
        }

        // then
        assertDoesNotThrow(() -> memberCouponService.create(coupon.getId(), member.getId()));
    }

    @Test
    @DisplayName("동일한 쿠폰을 발행한 횟수가 5회 이상이면 동일 쿠폰 발행이 불가능하다.")
    void should_not_create_coupon_when_created_same_coupon_over_5_times() {
        // given
        Coupon coupon = CouponFixture.createCoupon();
        couponRepository.save(coupon);
        Member member = MemberFixture.createMember();
        memberRepository.save(member);

        // when
        for (int i = 0; i < 5; i++) {
            memberCouponService.create(coupon.getId(), member.getId());
        }

        // then
        assertThatThrownBy(() -> memberCouponService.create(coupon.getId(), member.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("더 이상 발급할 수 없는 쿠폰입니다. 최대 발급 횟수 : " + MAX_ISSUE_COUNT);
    }
}
