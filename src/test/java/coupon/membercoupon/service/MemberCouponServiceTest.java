package coupon.membercoupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

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
        assertThat(savedMemberCoupon.getMemberId()).isEqualTo(1L);
        assertThat(savedMemberCoupon.getCoupon().getNameValue()).isEqualTo("싱싱한 켈리 할인 쿠폰");
    }
}
