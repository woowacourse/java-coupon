package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class IssuancePeriodTest {

    @Test
    @DisplayName("쿠폰 발급 가능 기간 확인 성공")
    void canIssue() {
        IssuancePeriod issuancePeriod = new IssuancePeriod(LocalDate.parse("2024-01-01"), LocalDate.parse("2025-01-01"));

        assertTrue(issuancePeriod.canIssue(LocalDateTime.parse("2024-01-01T00:00:00.000000")));
        assertTrue(issuancePeriod.canIssue(LocalDateTime.parse("2025-01-01T23:59:59.999999")));
    }

    @Test
    @DisplayName("쿠폰 발급 가능 기간 생성 실패: 쿠폰 발급 가능 기간이 Null 인 경우")
    void createIssuancePeriodWhenNull() {
        assertThatThrownBy(() -> new IssuancePeriod(null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 발행 시작일 또는 종료일은 null이 될 수 없습니다.");
    }
}
