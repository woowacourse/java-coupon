package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @DisplayName("회원 쿠폰을 생성한다.")
    @Test
    void createMemberCouponTest() {
        // given
        Member member = new Member("망쵸");
        Coupon coupon = new Coupon("망쵸 쿠폰", 1000, 10000);
        MemberCoupon memberCoupon = new MemberCoupon(member, coupon);
        LocalDateTime dayAfterWeek = LocalDateTime.now().plusDays(6).with(LocalTime.MAX);

        // when, then
        assertThat(memberCoupon.getExpiredAt()).isEqualTo(dayAfterWeek);
    }
}
