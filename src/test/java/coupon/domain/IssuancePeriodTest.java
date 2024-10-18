package coupon.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import coupon.exception.CouponException;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IssuancePeriodTest {

    private final LocalDate today = LocalDate.now();
    private final LocalDate tomorrow = today.plusDays(1);

    @DisplayName("발급 기간을 생성한다.")
    @Test
    void create() {
        IssuancePeriod issuancePeriod = new IssuancePeriod(today, tomorrow);

        assertEquals(today, issuancePeriod.getIssuanceStart());
        assertEquals(tomorrow, issuancePeriod.getIssuanceEnd());
    }

    @DisplayName("발급일이 만료일보다 이후일 경우 예외가 발생한다.")
    @Test
    void createInvalidPeriod() {
        assertThrows(CouponException.class,
                () -> new IssuancePeriod(
                        tomorrow,
                        today
                )
        );
    }

    @DisplayName("날짜가 시작일 미만이면 발급 불가능하다.")
    @Test
    void isIssuableBeforeIssuedAt() {
        IssuancePeriod issuancePeriod = new IssuancePeriod(today, tomorrow);

        boolean issuable = issuancePeriod.isIssuable(today.minusDays(1).atStartOfDay());

        assertFalse(issuable);
    }
}
