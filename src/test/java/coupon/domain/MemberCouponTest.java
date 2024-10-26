package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import coupon.domain.coupon.Coupon;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    private final Member member = new Member();

    @Test
    @DisplayName("멤버 쿠폰이 잘 발급된다.")
    void issue() {
        LocalDate today = LocalDate.now();
        Coupon coupon = new Coupon("쿠폰", 10000, 1000, "FOOD", today, today);
        assertDoesNotThrow(() -> MemberCoupon.issue(coupon, member));
    }

    @Test
    @DisplayName("쿠폰 발급 가능 기간이 아닐 때 멤버 쿠폰을 발급하려 하면 예외가 발생한다,")
    void validateCouponCanIssue() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        Coupon coupon = new Coupon("쿠폰", 10000, 1000, "FOOD", yesterday, yesterday);
        assertThatThrownBy(() -> MemberCoupon.issue(coupon, member))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("쿠폰 발급 가능 기간이 아닙니다.");
    }

    @Test
    @DisplayName("이미 사용된 멤버 쿠폰을 사용하려 하면 예외가 발생한다.")
    void useWhenAlreadyUsed() {
        LocalDate today = LocalDate.now();
        Coupon coupon = new Coupon("쿠폰", 10000, 1000, "FOOD", today, today);
        MemberCoupon memberCoupon = MemberCoupon.issue(coupon, member);

        memberCoupon.use();

        assertThatThrownBy(memberCoupon::use)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 사용된 쿠폰입니다.");
    }

    @Test
    @DisplayName("사용 가능한 기간이 아닌 멤버 쿠폰을 사용하려 하면 예외가 발생한다.")
    void useWhenNotInPeriodOfUse() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        Coupon coupon = new Coupon("쿠폰", 10000, 1000, "FOOD", yesterday, yesterday);
        MemberCoupon memberCoupon = new MemberCoupon(1L, coupon, member, false, yesterday, yesterday);

        assertThatThrownBy(memberCoupon::use)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("사용 가능한 기간이 아닙니다.");
    }
}
