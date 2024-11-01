package coupon.membercoupon.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.stream.IntStream;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import coupon.coupon.application.CouponService;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponRepository;
import coupon.coupon.domain.Coupons;
import coupon.fixture.CouponFixture;
import coupon.member.domain.Member;
import coupon.member.domain.MemberRepository;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.domain.MemberCouponRepository;
import coupon.membercoupon.domain.MemberCoupons;
import coupon.membercoupon.dto.FindAllMemberCouponResponse;

@SpringBootTest
@Transactional
class MemberCouponFacadeTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberCouponService memberCouponService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private MemberCouponFacade memberCouponFacade;
    @Autowired
    private MemberCouponRepository memberCouponRepository;

    private Member member;
    private Coupon coupon;
    private LocalDate issueStartDate;

    @BeforeEach
    void setUp() {
        issueStartDate = LocalDate.parse("2024-10-11");
        member = memberRepository.save(new Member("hi"));
        coupon = couponService.create(
                new Coupon("행복한 50% 쿠폰", 1_000L, 10_000L, issueStartDate, issueStartDate.plusDays(7)));
    }

    @Test
    @DisplayName("멤버에게 쿠폰 발급된 쿠폰 목록 조회")
    void getMemberCouponsByMemberId() {
        IntStream.range(0, 5)
                .forEach(ignore -> memberCouponService.createMemberCoupon(
                        new MemberCoupon(member.getId(), coupon.getId(), false, issueStartDate.plusDays(3))));

        FindAllMemberCouponResponse memberCouponsResponses = memberCouponFacade.getMemberCouponsByMemberId(
                member.getId());

        assertThat(memberCouponsResponses.memberCoupons()).hasSize(5);
        assertThat(memberCouponsResponses.memberCoupons().get(0).coupon().getName())
                .isEqualTo(coupon.getName());
    }

    @Nested
    class CacheCouponTest {

        @SpyBean
        private CouponRepository couponRepository;

        @Test
        @DisplayName("쿠폰 조회: 캐시 적용 확인")
        void getCouponShouldUseCachedData() {
            Coupon savedCoupon = couponService.create(CouponFixture.createCouponWithId());
            MemberCoupon memberCoupon = new MemberCoupon(member.getId(), savedCoupon.getId(), false, issueStartDate.plusDays(3));
            memberCouponService.createMemberCoupon(memberCoupon);

            FindAllMemberCouponResponse response = memberCouponFacade.getMemberCouponsByMemberId(member.getId());

            verify(couponRepository, never()).fetchById(savedCoupon.getId());
            assertThat(response).isEqualTo(FindAllMemberCouponResponse.of(new MemberCoupons(memberCoupon), new Coupons(savedCoupon)));
        }
    }
}
