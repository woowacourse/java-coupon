package coupon.membercoupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.ProductionCategory;
import coupon.member.domain.Member;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.mock.FakeCouponRepository;
import coupon.mock.FakeMemberCouponRepository;
import coupon.mock.FakeMemberRepository;

class MemberCouponServiceTest {

    private FakeMemberRepository memberRepository;
    private FakeCouponRepository couponRepository;
    private FakeMemberCouponRepository memberCouponRepository;
    private MemberCouponService memberCouponService;

    @BeforeEach
    public void setUp() {
        final FakeMemberRepository fakeMemberRepository = new FakeMemberRepository();
        final FakeCouponRepository fakeCouponRepository = new FakeCouponRepository();
        final FakeMemberCouponRepository fakeMemberCouponRepository = new FakeMemberCouponRepository();
        this.memberRepository = fakeMemberRepository;
        this.couponRepository = fakeCouponRepository;
        this.memberCouponRepository = fakeMemberCouponRepository;
        this.memberCouponService = new MemberCouponService(
                fakeMemberCouponRepository,
                memberRepository,
                couponRepository
        );
    }

    @DisplayName("회원 쿠폰을 발급한다.")
    @Test
    void issueCoupon() {
        // Given
        final Member member = new Member(1L, "kelly");
        final Coupon coupon = Coupon.create(
                "싱싱한 켈리 할인 쿠폰",
                10000,
                1000,
                ProductionCategory.FOOD,
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(3)
        );
        memberRepository.storage.add(member);
        couponRepository.storage.add(coupon);

        // When
        memberCouponService.issueCoupon(1L, "싱싱한 켈리 할인 쿠폰");

        // Then
        final MemberCoupon savedMemberCoupon = memberCouponRepository.storage.get(0);
        assertThat(savedMemberCoupon.giveMemberId()).isEqualTo(1L);
        assertThat(savedMemberCoupon.getCoupon().giveNameValue()).isEqualTo("싱싱한 켈리 할인 쿠폰");
    }

    @DisplayName("특정 회원의 쿠폰 정보를 모두 조회한다.")
    @Test
    void getMemberCoupons() {
        // Given
        final Member member = new Member(1L, "kelly");
        final Coupon couponA = Coupon.create(
                "싱싱한 켈리 할인 쿠폰",
                10000,
                1000,
                ProductionCategory.FOOD,
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(3)
        );
        final Coupon couponB = Coupon.create(
                "싱싱한 도라 할인 쿠폰",
                10000,
                1000,
                ProductionCategory.FOOD,
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(3)
        );
        memberRepository.storage.add(member);
        couponRepository.storage.add(couponA);
        couponRepository.storage.add(couponB);

        memberCouponService.issueCoupon(1L, "싱싱한 켈리 할인 쿠폰");
        memberCouponService.issueCoupon(1L, "싱싱한 켈리 할인 쿠폰");
        memberCouponService.issueCoupon(1L, "싱싱한 도라 할인 쿠폰");
        memberCouponService.issueCoupon(1L, "싱싱한 도라 할인 쿠폰");
        memberCouponService.issueCoupon(1L, "싱싱한 도라 할인 쿠폰");

        // When
        final List<MemberCoupon> memberCoupons = memberCouponService.getMemberCoupons(1L);

        // Then
        assertSoftly(softly -> {
            softly.assertThat(memberCoupons.size()).isEqualTo(5);

            final MemberCoupon memberCoupon = memberCoupons.get(0);
            softly.assertThat(memberCoupon.giveMemberId()).isEqualTo(1L);
            softly.assertThat(memberCoupon.getCoupon().giveNameValue()).isEqualTo("싱싱한 켈리 할인 쿠폰");
            softly.assertThat(memberCoupon.getIsUsed()).isEqualTo(false);
        });
    }
}
