package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @Test
    @DisplayName("쿠폰을 사용할 수 있다.")
    void use() {
        MemberCoupon memberCoupon = MemberCoupon.issue(1L, 1L);

        memberCoupon.use();

        assertThat(memberCoupon.getIsUsed()).isTrue();
    }

    @Test
    @DisplayName("한 번 사용된 쿠폰은 다시 사용이 불가능하다.")
    void alreadyUsed() {
        MemberCoupon memberCoupon = MemberCoupon.issue(1L, 1L);
        memberCoupon.use();

        assertThatThrownBy(memberCoupon::use)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 사용된 쿠폰입니다.");
    }

    @Test
    @DisplayName("쿠폰의 사용 기간이 지나면 쿠폰을 사용할 수 없다.")
    void validateDuration() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime weekAgo = today.minusDays(7);
        LocalDateTime expiresAt = weekAgo.plusDays(6);
        MemberCoupon memberCoupon = new MemberCoupon(null, 1L, 1L, false, weekAgo, expiresAt);

        assertThatThrownBy(memberCoupon::use)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("사용 기간이 지난 쿠폰입니다.");
    }

    @Test
    @DisplayName("쿠폰의 사용 가능 기간은 발행일 포함 7일이다.")
    void usableDuration() {
        MemberCoupon memberCoupon = MemberCoupon.issue(1L, 1L);

        LocalDateTime afterSixDay = LocalDateTime.now().plusDays(6);
        int actual = memberCoupon.getExpiresAt().getDayOfMonth();
        assertThat(actual).isEqualTo(afterSixDay.getDayOfMonth());
    }
}
