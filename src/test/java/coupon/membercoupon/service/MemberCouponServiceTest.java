package coupon.membercoupon.service;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import coupon.CouponException;
import coupon.ServiceTest;
import coupon.coupon.domain.Coupon;
import coupon.fixture.CouponFixture;
import coupon.fixture.MemberFixture;
import coupon.member.domain.Member;
import coupon.membercoupon.domain.MemberCoupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class MemberCouponServiceTest extends ServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @DisplayName("사용자에게 쿠폰을 발급한다.")
    @Test
    void issueCoupon() {
        // given
        Coupon coupon = saveCoupon();
        Member member = saveMember();

        // when
        memberCouponService.issue(member, coupon);

        // then
        assertThat(memberCouponRepository.findAllByMemberIdAndCouponId(member.getId(), coupon.getId())).hasSize(1);
    }

    @DisplayName("한 회원이 5장 초과로 쿠폰 발급을 시도하면 예외가 발생한다.")
    @Test
    void cannotIssueCouponIfExceedCount() {
        // given
        Coupon coupon = saveCoupon();
        Member member = saveMember();
        for (int count = 1; count <= 5; count++) {
            memberCouponService.issue(member, coupon);
        }

        // when & then
        assertThatThrownBy(() -> memberCouponService.issue(member, coupon))
                .isInstanceOf(CouponException.class)
                .hasMessage("동일한 쿠폰을 5장 이상 발급할 수 없어요.");
    }

    @DisplayName("사용자에게 발행된 모든 쿠폰 정보를 가져온다.")
    @Test
    void getCouponsByMember() {
        // given
        Coupon coupon = saveCoupon();
        Member member = saveMember();
        memberCouponRepository.save(MemberCoupon.issue(member, coupon));

        // when
        List<Coupon> result = memberCouponService.findAllCouponByMember(member);

        // then
        assertThat(result).hasSize(1);
    }

    private Coupon saveCoupon() {
        LocalDate startAt = LocalDate.now();
        LocalDate endAt = LocalDate.now().plusDays(6);
        Coupon coupon = CouponFixture.create(startAt, endAt);
        couponRepository.save(coupon);
        return coupon;
    }

    private Member saveMember() {
        Member member = MemberFixture.create();
        memberRepository.save(member);
        return member;
    }
}
