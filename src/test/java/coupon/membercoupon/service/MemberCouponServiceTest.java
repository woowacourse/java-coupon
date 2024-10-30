package coupon.membercoupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.coupon.domain.Coupon;
import coupon.coupon.service.CouponService;
import coupon.member.domain.Member;
import coupon.member.service.MemberService;
import coupon.membercoupon.domain.MemberCoupons;

@SpringBootTest
public class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CouponService couponService;

    private Member member;
    private Coupon coupon;

    @BeforeEach
    void setUp() {
        member = new Member("member_name");
        coupon = new Coupon("coupon_name", 1000L, 10000L, "FOOD", LocalDateTime.now(), LocalDateTime.now());

        memberService.createWithCache(member);
        couponService.createWithCache(coupon);
    }

    @DisplayName("멤버 ID에 해당하는 멤버가 없으면 멤버 쿠폰을 만들 수 없다.")
    @Test
    void testCreatThrowErrorWhenMemberNotExist() {
        // given & when & then
        assertThatThrownBy(() -> memberCouponService.create(member.getId() + 1, coupon.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당하는 멤버가 없습니다.");
    }

    @DisplayName("쿠폰 ID에 해당하는 쿠폰이 없으면 멤버 쿠폰을 만들 수 없다.")
    @Test
    void testCreateThrowErrorWhenCouponNotExist() {
        // given & when & then
        assertThatThrownBy(() -> memberCouponService.create(member.getId(), coupon.getId() + 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당하는 쿠폰이 없습니다.");
    }

    @DisplayName("한 명의 회원이 동일한 쿠폰을 사용한 쿠폰을 포함하여 6장 이상으로 가질 수 없다.")
    @Test
    void testCreateThrowErrorWhenOverFiveCouponsPerMember() {
        // given
        for (int i = 1; i <= 5; i++) {
            memberCouponService.create(member.getId(), coupon.getId());
        }

        // when & then
        assertThatThrownBy(() -> memberCouponService.create(member.getId(), coupon.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("한 명의 회원이 동일한 쿠폰을 사용한 쿠폰을 포함하여 5장 초과로 발급할 수 없습니다.");
    }

    @DisplayName("멤버 ID에 해당하는 멤버 쿠폰만 불러올 수 있다.")
    @Test
    void testReadAllByMemberId() {
        // given
        Member anotherMember = new Member("another_member_name");
        memberService.createWithCache(anotherMember);
        memberCouponService.create(member.getId(), coupon.getId());
        memberCouponService.create(anotherMember.getId(), coupon.getId());

        // when
        MemberCoupons memberCoupons = memberCouponService.readAllByMemberId(member.getId());

        // then
        assertThat(memberCoupons.getMemberCoupons()).hasSize(1);
    }
}
