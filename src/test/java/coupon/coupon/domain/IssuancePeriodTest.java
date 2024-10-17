package coupon.coupon.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class IssuancePeriodTest {

    @Test
    void canIssue() {
        IssuancePeriod issuancePeriod = new IssuancePeriod(LocalDate.parse("2024-01-01"), LocalDate.parse("2025-01-01"));

        assertTrue(issuancePeriod.canIssue(LocalDateTime.parse("2024-01-01T00:00:00.000000")));
        assertTrue(issuancePeriod.canIssue(LocalDateTime.parse("2025-01-01T23:59:59.999999")));
    }
}
