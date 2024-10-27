package coupon.coupon.domain;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MemberCouponTest {

    @DisplayName("쿠폰 발급 일시로 만료 일시를 구한다.")
    @Test
    void calculateExpiredAt() {
        // given
        Coupon coupon = new Coupon("name", 1000, 30000, Category.FASHION, LocalDate.now(), LocalDate.now()
                .plusDays(10));
        Member member = new Member("Prin?");

        // when
        MemberCoupon memberCoupon = MemberCoupon.issue(member, coupon);

        //then
        assertThat(memberCoupon.getIssuedAt().plusDays(7)).isBeforeOrEqualTo(memberCoupon.getExpiredAt());
    }
}
