package coupon.domain.coupon;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponTest {

    private final LocalDate today = LocalDate.now();
    private final LocalDate tomorrow = today.plusDays(1);

    @DisplayName("쿠폰 발급이 가능한지 확인한다.")
    @Test
    void isIssuable() {
        Coupon coupon = new Coupon(
                1L,
                "쿠폰 이름",
                10000,
                1000,
                "FOOD",
                today,
                tomorrow
        );

        assertTrue(coupon.isIssuable(today.atStartOfDay()));
        assertTrue(coupon.isIssuable(tomorrow.atTime(LocalTime.MAX)));
    }

    @DisplayName("발급 일시가 쿠폰 발급 시작일 이전이면 발급할 수 없다.")
    @Test
    void isIssuableBeforeIssuanceStart() {
        Coupon coupon = new Coupon(
                1L,
                "쿠폰 이름",
                10000,
                1000,
                "FOOD",
                today,
                tomorrow
        );

        assertFalse(coupon.isIssuable(today.minusDays(1).atStartOfDay()));
    }
}
