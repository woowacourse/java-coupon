package coupon.membercoupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import coupon.coupon.Category;
import coupon.coupon.Coupon;
import coupon.coupon.CouponRepository;
import coupon.member.Member;
import coupon.member.MemberRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberCouponServiceTest {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private  MemberCouponService memberCouponService;

    @Test
    void 회원_쿠폰_발급() {
        // given
        Coupon coupon = couponRepository.save(new Coupon("name", 1000, 5000, Category.FOOD,
                LocalDateTime.now(), LocalDateTime.now().plusDays(1)));

        Member member = memberRepository.save(new Member("name"));

        // when
        MemberCoupon result = memberCouponService.issueMemberCoupon(coupon.getId(), member.getId());

        // then
        assertThat(result).isNotNull();
    }

    @Test
    void 회원_쿠폰_발급시_잘못된_쿠폰ID일_경우_예외() {
        // given
        long wrongCouponId = 0L;
        Member member = memberRepository.save(new Member("name"));

        // when, then
        assertThatThrownBy(() -> memberCouponService.issueMemberCoupon(wrongCouponId, member.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 회원_쿠폰_발급시_잘못된_회원ID일_경우_예외() {
        // given
        Coupon coupon = couponRepository.save(new Coupon("name", 1000, 5000, Category.FOOD,
                LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        long wrongMemberId = 0L;

        // when, then
        assertThatThrownBy(() -> memberCouponService.issueMemberCoupon(coupon.getId(), wrongMemberId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 회원_쿠폰_발급시_발급일이_지난_쿠폰일_경우_예외() {
        // given
        Coupon coupon = mock(Coupon.class);
        when(coupon.isCouponIssuable()).thenReturn(false);

        CouponRepository mockCouponRepository = mock(CouponRepository.class);
        when(mockCouponRepository.findById(coupon.getId())).thenReturn(Optional.of(coupon));

        Member member = memberRepository.save(new Member("name"));

        // when, then
        assertThatThrownBy(() -> memberCouponService.issueMemberCoupon(coupon.getId(), member.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 회원_쿠폰_발급시_회원에_발급된_쿠폰이_다섯장일경우_예외() {
        // given
        Coupon coupon = couponRepository.save(new Coupon("name", 1000, 5000, Category.FOOD,
                LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        Member member = memberRepository.save(new Member("name"));

        memberCouponService.issueMemberCoupon(coupon.getId(), member.getId());
        memberCouponService.issueMemberCoupon(coupon.getId(), member.getId());
        memberCouponService.issueMemberCoupon(coupon.getId(), member.getId());
        memberCouponService.issueMemberCoupon(coupon.getId(), member.getId());
        memberCouponService.issueMemberCoupon(coupon.getId(), member.getId());

        // when, then
        assertThatThrownBy(() -> memberCouponService.issueMemberCoupon(coupon.getId(), member.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 회원에_발급된_쿠폰_목록을_조회() {
        // given
        Coupon coupon = couponRepository.save(new Coupon("name", 1000, 5000, Category.FOOD,
                LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        Member member = memberRepository.save(new Member("name"));

        memberCouponService.issueMemberCoupon(coupon.getId(), member.getId());
        memberCouponService.issueMemberCoupon(coupon.getId(), member.getId());
        memberCouponService.issueMemberCoupon(coupon.getId(), member.getId());
        memberCouponService.issueMemberCoupon(coupon.getId(), member.getId());
        memberCouponService.issueMemberCoupon(coupon.getId(), member.getId());

        // when
        MemberCouponResponse result = memberCouponService.findMemberCoupons(member.getId());

        // then
        assertThat(result.coupons()).hasSize(5);
    }
}
