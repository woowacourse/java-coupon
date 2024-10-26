package coupon.service.member_coupon;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.discount.DiscountType;
import coupon.domain.coupon.repository.CouponRepository;
import coupon.domain.member.Member;
import coupon.domain.member.repository.MemberRepository;
import coupon.exception.MemberCouponIssueLimitException;
import coupon.service.coupon.dto.CouponIssueResponse;
import coupon.service.member_coupon.dto.MemberCouponResponse;
import coupon.service.member_coupon.dto.MemberCouponsResponse;
import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberCouponServiceTest {

    @Autowired
    MemberCouponService memberCouponService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CouponRepository couponRepository;

    private Coupon coupon;

    @BeforeEach
    void issueCoupon() {
        String couponName = "testCoupon";
        int discountPrice = 1000;
        int minOrderPrice = 5000;
        int minDiscountRange = 3;
        int maxDiscountRange = 20;
        Category category = Category.FASHION;
        LocalDate issueStartDate = LocalDate.now().minusDays(3);
        LocalDate issueEndDate = LocalDate.now().plusDays(3);

        coupon = couponRepository.save(
                new Coupon(couponName, DiscountType.PERCENT, minDiscountRange, maxDiscountRange, discountPrice,
                minOrderPrice, category, issueStartDate, issueEndDate)
        );
    }

    @Test
    void 쿠폰을_발급한다() {
        // given
        Member member = memberRepository.save(new Member());

        // when
        CouponIssueResponse couponIssueResponse = memberCouponService.issueMemberCoupon(member.getId(), coupon.getId());

        // then
        Assertions.assertThat(couponIssueResponse.couponName()).isEqualTo(coupon.getCouponName());
    }

    @Test
    void 쿠폰을_5회_초과로_발급받을_수_없다() {
        //given
        Member member = memberRepository.save(new Member());
        memberCouponService.issueMemberCoupon(member.getId(), coupon.getId());
        memberCouponService.issueMemberCoupon(member.getId(), coupon.getId());
        memberCouponService.issueMemberCoupon(member.getId(), coupon.getId());
        memberCouponService.issueMemberCoupon(member.getId(), coupon.getId());
        memberCouponService.issueMemberCoupon(member.getId(), coupon.getId());

        // when & then
        Assertions.assertThatThrownBy(() -> memberCouponService.issueMemberCoupon(member.getId(), coupon.getId()))
                .isExactlyInstanceOf(MemberCouponIssueLimitException.class);
    }

    @Test
    @Transactional
    void 회원의_쿠폰_정보를_조회한다() {
        // given
        MemberCouponService memberCouponService = mock(MemberCouponService.class);
        Member member = memberRepository.save(new Member());
        List<MemberCouponResponse> memberCouponResponses = List.of(
                new MemberCouponResponse(1L, 1L, "테스트쿠폰", Category.FOOD.getTypeName(),
                        false, LocalDate.now(), LocalDate.now()));

        when(memberCouponService.getMemberCoupons(anyLong()))
                .thenReturn(new MemberCouponsResponse(memberCouponResponses));

        // then
        Assertions.assertThat(memberCouponService.getMemberCoupons(member.getId()).memberCoupons()).hasSize(1);
    }
}
