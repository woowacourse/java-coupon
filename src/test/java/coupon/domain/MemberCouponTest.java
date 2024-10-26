package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import coupon.domain.coupon.Coupon;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    private Coupon coupon;
    private Member member;

    @BeforeEach
    void setup() {
        member = new Member();
    }

    @Test
    @DisplayName("멤버 쿠폰이 잘 발급된다.")
    void issue() {
        LocalDate today = LocalDate.now();
        coupon = new Coupon("쿠폰", 10000, 1000, "FOOD", today, today);
        assertDoesNotThrow(() -> MemberCoupon.issue(coupon, member));
    }

    @Test
    @DisplayName("쿠폰 발급 가능 기간이 아닐 때 멤버 쿠폰을 발급하려 하면 예외가 발생한다,")
    void validateCouponCanIssue() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        coupon = new Coupon("쿠폰", 10000, 1000, "FOOD", yesterday, yesterday);
        assertThatThrownBy(() -> MemberCoupon.issue(coupon, member))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("쿠폰 발급 가능 기간이 아닙니다.");
    }
}
