package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @DisplayName("회원 쿠폰을 생성한다.")
    @Test
    void createMemberCouponTest() {
        // given
        Member member = new Member("망쵸");
        Coupon coupon = new Coupon("망쵸 쿠폰", 1000, 10000);
        MemberCoupon memberCoupon = MemberCoupon.issue(member, coupon);

        // when, then
        assertThat(memberCoupon.getExpiredAt().toString()).endsWith("23:59:59.999999");
    }
}
