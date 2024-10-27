package coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @DisplayName("회원 쿠폰을 생성한다.")
    @Test
    void create() {
        assertThatCode(() -> new MemberCoupon(1L, 1L, false, LocalDateTime.now()))
                .doesNotThrowAnyException();
    }

    @DisplayName("회원 쿠폰을 생성하면 사용 여부는 false, 만료일시는 생성일로부터 7일 뒤 자정 직전까지다.")
    @Test
    void validate() {
        LocalDateTime today = LocalDateTime.now();
        MemberCoupon memberCoupon = new MemberCoupon(1L, 1L, false, today);

        LocalDateTime expected = today.toLocalDate().plusDays(6).atTime(23, 59, 59, 999999999);

        assertThat(memberCoupon.isUsed()).isFalse();
        assertThat(memberCoupon.getExpirationDateTime()).isEqualTo(expected);
    }
}
