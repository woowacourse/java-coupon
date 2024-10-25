package coupon.domain.member_coupon;

import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.discount.DiscountType;
import coupon.domain.member.Member;
import coupon.exception.CouponIssueDateException;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @Test
    void 쿠폰_발급기간이_아니라면_쿠폰을_발급받을_수_없다() {
        Member member = new Member();
        int minDiscountRange = 3;
        int maxDiscountRange = 20;
        LocalDate today = LocalDate.now();
        LocalDate issueStartDate = today.minusDays(1);
        LocalDate issueEndDate = today.minusDays(1);

        Coupon coupon = new Coupon(
                "testCoupon",
                DiscountType.PERCENT,
                minDiscountRange,
                maxDiscountRange,
                1000,
                5000,
                Category.FASHION,
                issueStartDate,
                issueEndDate);

        Assertions.assertThatThrownBy(() -> MemberCoupon.issue(member.getId(), coupon))
                .isExactlyInstanceOf(CouponIssueDateException.class);
    }
}
