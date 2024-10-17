package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @Test
    @DisplayName("사용자에게 발행된 쿠폰은 발행일로부터 7일 이내에 사용할 수 있다.")
    void validateCouponExpiration() {
        Coupon coupon = new Coupon("coupon", 1000, 10000, Category.FASHION);
        Boolean isNotUsed = Boolean.FALSE;
        LocalDateTime issuedDateTime = LocalDateTime.now().minusDays(8L);

        assertThatThrownBy(() -> new MemberCoupon(coupon, isNotUsed, issuedDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰이 만료되었습니다.");
    }
}
