package coupon.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import coupon.exception.CouponException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IssuancePeriodTest {

    @DisplayName("발급 기간을 생성한다.")
    @Test
    void create() {
        IssuancePeriod issuancePeriod = new IssuancePeriod(
                LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 2)
        );

        assertEquals(LocalDate.of(2025, 1, 1), issuancePeriod.getIssuedAt());
        assertEquals(LocalDate.of(2025, 1, 2), issuancePeriod.getExpiredAt());
    }

    @DisplayName("발급일이 만료일보다 이후일 경우 예외가 발생한다.")
    @Test
    void createInvalidPeriod() {
        assertThrows(CouponException.class,
                () -> new IssuancePeriod(
                        LocalDate.of(2025, 1, 2),
                        LocalDate.of(2025, 1, 1)
                )
        );
    }

    @DisplayName("날짜가 시작일 미만이면 발급 불가능하다.")
    @Test
    void isIssuableBeforeIssuedAt() {
        IssuancePeriod issuancePeriod = new IssuancePeriod(
                LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 2)
        );

        boolean issuable = issuancePeriod.isIssuable(LocalDateTime.of(2024, 12, 31, 23, 59));

        assertFalse(issuable);
    }

    @DisplayName("날짜가 만료일 이후이면 만료되었다.")
    @Test
    void isExpiredAfterExpiredAt() {
        IssuancePeriod issuancePeriod = new IssuancePeriod(
                LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 2)
        );

        boolean expired = issuancePeriod.isExpired(LocalDateTime.of(2025, 1, 3, 0, 0));

        assertTrue(expired);
    }
}
