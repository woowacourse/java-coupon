package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {
    
    @DisplayName("회원에게 발급된 쿠폰의 발급 일시로 만료 일시를 계산한다.")
    @Test
    void calculateExpireAt() {
        // given
        String name = "냥인의쿠폰";
        BigDecimal discountAmount = BigDecimal.valueOf(1_000);
        BigDecimal minimumOrderPrice = BigDecimal.valueOf(5_000);
        CouponCategory couponCategory = CouponCategory.FOOD;
        LocalDateTime issueStartedAt = LocalDateTime.of(2024, 10, 16, 0, 0, 0, 0);
        LocalDateTime issueEndedAt = LocalDateTime.of(2024, 10, 26, 23, 59, 59, 999_999_000);
        Coupon coupon = new Coupon(
                name, discountAmount, minimumOrderPrice, couponCategory, issueStartedAt, issueEndedAt);
        Long memberId = 1L;

        // when
        MemberCoupon memberCoupon = new MemberCoupon(coupon, memberId);
        LocalDateTime actual = memberCoupon.getExpireAt();

        // then
        assertThat(actual).isAfter(memberCoupon.getIssuedAt());
    }
}
