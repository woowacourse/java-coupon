package coupon.membercoupon.domain;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import coupon.coupon.domain.Coupon;
import coupon.fixture.CouponFixture;
import coupon.fixture.MemberFixture;
import coupon.member.domain.Member;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MemberCouponTest {

    @DisplayName("쿠폰 발급 일시로 만료 일시를 구한다.")
    @Test
    void calculateExpiredAt() {
        // given
        Coupon coupon = CouponFixture.create(LocalDate.now(), LocalDate.now().plusDays(10));
        Member member = MemberFixture.create();

        // when
        MemberCoupon memberCoupon = MemberCoupon.issue(member, coupon);

        //then
        assertThat(memberCoupon.getIssuedAt().plusDays(7)).isBeforeOrEqualTo(memberCoupon.getExpiredAt());
    }
}
