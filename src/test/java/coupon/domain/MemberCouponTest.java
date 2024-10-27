package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @Test
    @DisplayName("이미 사용된 멤버 쿠폰을 사용하려 하면 예외가 발생한다.")
    void useWhenAlreadyUsed() {
        MemberCoupon memberCoupon = MemberCoupon.issue(1L, 1L);

        memberCoupon.use();

        assertThatThrownBy(memberCoupon::use)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 사용된 쿠폰입니다.");
    }

    @Test
    @DisplayName("사용 가능한 기간이 아닌 멤버 쿠폰을 사용하려 하면 예외가 발생한다.")
    void useWhenNotInPeriodOfUse() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        MemberCoupon memberCoupon = new MemberCoupon(1L, 1L, 1L, false, yesterday, yesterday);

        assertThatThrownBy(memberCoupon::use)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("사용 가능한 기간이 아닙니다.");
    }
}
