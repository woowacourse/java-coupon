package coupon.membercoupon.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExpirationDateTest {

    @Test
    @DisplayName("만료 일의 23:59:59.999999 까지 사용 가능")
    void isExpired() {
        final ExpirationDate expirationDate = new ExpirationDate(LocalDate.parse("2024-01-01"));

        assertTrue(expirationDate.isNotExpiate(LocalDateTime.parse("2024-01-01T00:00:00.000000")));
    }
}
