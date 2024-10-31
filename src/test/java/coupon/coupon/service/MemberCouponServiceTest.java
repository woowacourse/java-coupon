package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponCategory;
import coupon.coupon.domain.MemberCoupon;
import coupon.coupon.dto.MemberCouponResponse;
import coupon.member.Member;
import coupon.member.service.MemberService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("사용자 쿠폰 서비스")
class MemberCouponServiceTest {

    private final CouponService couponService;
    private final MemberCouponService memberCouponService;
    private final MemberService memberService;

    @Autowired
    public MemberCouponServiceTest(
            CouponService couponService,
            MemberCouponService memberCouponService,
            MemberService memberService
    ) {
        this.couponService = couponService;
        this.memberCouponService = memberCouponService;
        this.memberService = memberService;
    }

    @DisplayName("사용자 쿠폰을 발급한다.")
    @Test
    void issueMemberCoupon() {
        // given
        Member member = new Member("사용자");
        Coupon coupon = new Coupon(
                "쿠폰",
                CouponCategory.FASHION,
                1000,
                30000,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(3L)
        );

        Member savedMember = memberService.createMember(member);
        Coupon savedCoupon = couponService.createCoupon(coupon);

        MemberCoupon memberCoupon = new MemberCoupon(savedMember, savedCoupon, LocalDateTime.now().plusDays(5L));

        // when
        MemberCoupon actual = memberCouponService.issueMemberCoupon(memberCoupon);

        // then
        assertThat(actual.getId()).isEqualTo(1L);
    }

    @DisplayName("사용자 쿠폰은 5개 이상 발급될 수 없다.")
    @Test
    void validateIssueMemberCoupon() {
        // given
        Member member = new Member("사용자");
        Coupon coupon = new Coupon(
                "쿠폰",
                CouponCategory.FASHION,
                1000,
                30000,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(3L)
        );

        Member savedMember = memberService.createMember(member);
        Coupon savedCoupon = couponService.createCoupon(coupon);

        // when
        for (int i = 0; i < 5; i++) {
            MemberCoupon memberCoupon = new MemberCoupon(savedMember, savedCoupon, LocalDateTime.now().plusDays(5L));
            memberCouponService.issueMemberCoupon(memberCoupon);
        }

        // then
        MemberCoupon memberCoupon = new MemberCoupon(savedMember, savedCoupon, LocalDateTime.now().plusDays(5L));
        assertThatThrownBy(() -> memberCouponService.issueMemberCoupon(memberCoupon))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("사용자는 발급된 쿠폰을 확인할 수 있다.")
    @Test
    void readMemberCoupons() {
        // given
        Member member = new Member("사용자");
        Member anotherMember = new Member("다른사용자");

        Coupon coupon = new Coupon(
                "쿠폰",
                CouponCategory.FASHION,
                1000,
                30000,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(3L)
        );

        Member savedMember = memberService.createMember(member);
        Coupon savedCoupon = couponService.createCoupon(coupon);

        MemberCoupon memberCoupon = new MemberCoupon(savedMember, savedCoupon, LocalDateTime.now().plusDays(5L));
        memberCouponService.issueMemberCoupon(memberCoupon);

        MemberCoupon anotherMemberCoupon = new MemberCoupon(anotherMember, savedCoupon, LocalDateTime.now().plusDays(5L));
        memberCouponService.issueMemberCoupon(anotherMemberCoupon);

        // when
        List<MemberCouponResponse> memberCoupons = memberCouponService.readMemberCoupons(member);

        // then
        assertThat(memberCoupons).hasSize(1);
    }
}
