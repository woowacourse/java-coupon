package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import coupon.domain.Category;
import coupon.domain.MemberCoupon;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponName;
import coupon.domain.coupon.DiscountMount;
import coupon.domain.coupon.MinimumMount;
import coupon.domain.coupon.Period;
import coupon.domain.member.Member;
import coupon.domain.member.MemberName;
import coupon.dto.CouponAndMemberCouponResponse;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.MemberRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
class CouponServiceTest {

    @SpyBean
    private CouponService couponService;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private MemberCouponRepository memberCouponRepository;
    @Autowired
    private MemberRepository memberRepository;
    private Member member;

    @BeforeEach
    void truncateTables() {
        memberCouponRepository.deleteAll();
        couponRepository.deleteAll();
        memberRepository.deleteAll();
        member = memberRepository.save(new Member(new MemberName("포케리스웨트")));
    }

    @Test
    void 복제지연테스트() {
        Coupon coupon = couponService.create(new Coupon(
                new CouponName("쿠폰"),
                new DiscountMount(1000),
                new MinimumMount(5000),
                Category.FASHION,
                new Period(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1))
        ));
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }

    @DisplayName("존재하지 않는 쿠폰으로는 멤버의 쿠폰을 발급할 수 없다.")
    @Test
    void issueMemberCouponWithoutCoupon() {
        Member member = memberRepository.save(new Member(new MemberName("포케리스웨트")));

        Long notExistId = 2L;
        couponRepository.deleteById(notExistId);
        assertThatThrownBy(() -> couponService.issueMemberCoupon(notExistId, member))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 쿠폰 id입니다.");
    }

    @DisplayName("같은 쿠폰을 한 멤버가 5번 넘게 발급할 수 없다.")
    @Test
    void issueMemberCouponMoreThanLimit() {
        Coupon coupon = couponService.create(new Coupon(
                new CouponName("쿠폰"),
                new DiscountMount(1000),
                new MinimumMount(5000),
                Category.FASHION,
                new Period(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1))
        ));

        for (int i = 0; i < 5; ++i) {
            couponService.issueMemberCoupon(coupon.getId(), member);
        }
        assertThatThrownBy(() -> couponService.issueMemberCoupon(coupon.getId(), member))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰을 더 발급할 수 없습니다.");
    }

    @DisplayName("멤버의 발급된 쿠폰과 쿠폰 정보를 조회할 수 있다.")
    @Test
    void findCouponAndMemberCouponByMember() {
        Member otherMember = memberRepository.save(new Member(new MemberName("알파카프리썬")));
        Coupon coupon = couponService.create(new Coupon(
                new CouponName("쿠폰"),
                new DiscountMount(1000),
                new MinimumMount(5000),
                Category.FASHION,
                new Period(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1))
        ));
        MemberCoupon memberCoupon = couponService.issueMemberCoupon(coupon.getId(), member);
        MemberCoupon otherMemberCoupon = couponService.issueMemberCoupon(coupon.getId(), otherMember);

        CouponAndMemberCouponResponse actual = couponService.findCouponAndMemberCouponByMember(member);
        assertAll(
                () -> assertThat(actual.coupons().size())
                        .isEqualTo(1),
                () -> assertThat(actual.memberCoupons().size())
                        .isEqualTo(1),
                () -> assertThat(actual.coupons().get(0).getId())
                        .isEqualTo(coupon.getId()),
                () -> assertThat(actual.memberCoupons().get(0).getId())
                        .isEqualTo(memberCoupon.getId())
        );
    }

    @DisplayName("쿠폰을 캐싱해서 반환할 수 있다.")
    @Test
    void cacheable() {
        Coupon coupon = couponService.create(new Coupon(
                new CouponName("쿠폰"),
                new DiscountMount(1000),
                new MinimumMount(5000),
                Category.FASHION,
                new Period(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1))
        ));

        couponService.getCoupon(coupon.getId());
        // cache miss: 메서드가 실제로 호출된다
        verify(couponService, times(1)).getCoupon(coupon.getId());

        couponService.getCoupon(coupon.getId());
        // cache hit: 메서드가 실제로 호출되지 않고 캐시를 반환한다
        verify(couponService, times(1)).getCoupon(coupon.getId());
    }
}
