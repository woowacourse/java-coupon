package coupon.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @DisplayName("만료 일시를 올바르게 계산한다.")
    @Test
    void getExpiredAt() {
        LocalDateTime issuedAt = LocalDateTime.of(2024, 10, 16, 12, 0);
        MemberCoupon memberCoupon = new MemberCoupon(1L, 1L, false, issuedAt);

        LocalDateTime expirationDateTime = memberCoupon.getExpiredAt();

        LocalDateTime expectedExpiration = LocalDateTime.of(2024, 10, 23, 23, 59, 59, 999999000);
        assertEquals(expectedExpiration, expirationDateTime);
    }
}
