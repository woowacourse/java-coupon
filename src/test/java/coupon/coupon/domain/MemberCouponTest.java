package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @DisplayName("이미 사용된 쿠폰을 사용할 시 예외가 발생한다.")
    @Test
    void exception_WhenUseCouponThatIsUsed() {
        // given
        MemberCoupon memberCoupon = new MemberCoupon(1L, 1L, LocalDateTime.now());
        memberCoupon.useCoupon();

        // when & then
        assertThatThrownBy(memberCoupon::useCoupon).isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("만료된 쿠폰을 사용할 시 예외가 발생한다.")
    @Test
    void exception_WhenUseExpiredCoupon() {
        // given
        MemberCoupon memberCoupon = new MemberCoupon(1L, 1L, LocalDateTime.now().minusDays(10));

        // when & then
        assertThatThrownBy(memberCoupon::useCoupon).isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("쿠폰은 발급일 포함 유효기간동안 사용할 수 있다.")
    @Test
    void couponExpiredPeriod() {
        // given
        LocalDateTime issuedAt = LocalDateTime.of(2024, 10, 1, 10, 0);

        // when
        MemberCoupon memberCoupon = new MemberCoupon(1L, 1L, issuedAt);
        LocalDateTime expectedExpiryDate = issuedAt.plusDays(6)
                .withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        // then
        assertThat(memberCoupon.getExpiresAt()).isEqualTo(expectedExpiryDate);
    }

    @Test
    @DisplayName("만료일이 지나면 쿠폰은 더 이상 사용할 수 없다.")
    void testCouponExpiration() {
        // given
        LocalDateTime issuedAt = LocalDateTime.now().minusDays(8);

        // when
        MemberCoupon memberCoupon = new MemberCoupon(1L, 1L, issuedAt);

        // then
        assertThat(memberCoupon.getExpiresAt().isBefore(LocalDateTime.now())).isTrue();
    }
}
