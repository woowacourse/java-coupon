package membercoupon.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import coupon.domain.Coupon;
import coupon.exception.CouponException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    private final LocalDate today = LocalDate.now();
    private final Coupon coupon = new Coupon(
            1L,
            "쿠폰 이름",
            10000,
            1000,
            "FOOD",
            today.minusDays(10),
            today.plusDays(1)
    );

    @DisplayName("쿠폰의 발급 종료일 이후의 회원 쿠폰을 생성하면 예외가 발생한다.")
    @Test
    void createAfterIssuanceEnd() {
        assertThrows(
                CouponException.class,
                () -> new MemberCoupon(1L, 1L, coupon, today.plusDays(2).atStartOfDay())
        );
    }

    @DisplayName("회원에게 발급된 쿠폰은 발급일 포함 7일 23:59:59.999999 까지 사용 가능하다.")
    @Test
    void noExpired() {
        MemberCoupon memberCoupon = new MemberCoupon(1L, 1L, coupon, LocalDateTime.now());

        assertFalse(memberCoupon.isExpired());
    }

    @DisplayName("회원에게 발급된 쿠폰은 발급일 포함 7일 23:59:59.999999이 지나면 사용하지 못한다.")
    @Test
    void expired() {
        MemberCoupon memberCoupon = new MemberCoupon(1L, 1L, coupon, LocalDateTime.now().minusDays(7));

        assertTrue(memberCoupon.isExpired());
    }
}
