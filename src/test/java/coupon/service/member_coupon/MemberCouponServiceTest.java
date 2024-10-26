package coupon.service.member_coupon;

import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.discount.DiscountType;
import coupon.domain.coupon.repository.CouponRepository;
import coupon.domain.member.Member;
import coupon.domain.member.repository.MemberRepository;
import coupon.exception.MemberCouponIssueLimitException;
import coupon.service.coupon.dto.CouponIssueResponse;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
