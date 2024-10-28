package coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class MemberCouponTest {

    @DisplayName("멤버 쿠폰을 발급한다.")
    @Test
    void issue() {
        assertThatCode(() -> MemberCoupon.issue(1L, new Member(1L, "카피")))
                .doesNotThrowAnyException();
    }
}
