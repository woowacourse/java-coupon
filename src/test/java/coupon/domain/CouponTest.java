package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class CouponTest {

    @ParameterizedTest
    @ValueSource(strings = {"", "0123456789012345678901234567890"})
    @DisplayName("쿠폰명이 1자 이상 30자 이하가 아닐 경우 예외가 발생한다.")
    void validateName(String name) {
        assertThatThrownBy(() -> new Coupon(
                name,
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(10000),
                CouponCategory.FASHION,
                LocalDateTime.now().minusHours(1),
                LocalDateTime.now().plusHours(1)
        )).isInstanceOf(ConstraintViolationException.class)
                .hasMessageContainingAll("쿠폰명", "1자 이상", "30자 이하");
    }

    @ParameterizedTest
    @ValueSource(longs = {999, 10_001})
    @DisplayName("할인 금액에 1,000원 이상 10,000원 이하가 아닐 경우 예외가 발생한다.")
    void validateDiscountAmountRange(long discountAmount) {
        assertThatThrownBy(() -> new Coupon(
                "Jake",
                BigDecimal.valueOf(discountAmount),
                BigDecimal.valueOf(10000),
                CouponCategory.FASHION,
                LocalDateTime.now().minusHours(1),
                LocalDateTime.now().plusHours(1)
        )).isInstanceOf(ConstraintViolationException.class)
                .hasMessageContainingAll("할인 금액")
                .hasMessageFindingMatch("(1,000원 이상|10,000원 이하)");
    }

    @ParameterizedTest
    @ValueSource(longs = {4_999, 100_001})
    @DisplayName("최소 주문 금액이 5,000원 이상 100,000원 이하가 아닐 경우 예외가 발생한다.")
    void validateMinimumAmountRange(long minimumOrderAmount) {
        assertThatThrownBy(() -> new Coupon(
                "Jake",
                BigDecimal.valueOf(minimumOrderAmount / 10),
                BigDecimal.valueOf(minimumOrderAmount),
                CouponCategory.FASHION,
                LocalDateTime.now().minusHours(1),
                LocalDateTime.now().plusHours(1)
        )).isInstanceOf(ConstraintViolationException.class)
                .hasMessageContainingAll("최소 주문 금액")
                .hasMessageFindingMatch("(5,000원 이상|100,000원 이하)");
    }

    @ParameterizedTest
    @ValueSource(longs = {1004, 7942})
    @DisplayName("할인 금액이 500원 단위가 아닐 경우 예외가 발생한다.")
    void validateDiscountAmountUnit(long discountAmount) {
        assertThatThrownBy(() -> new Coupon(
                "Jake",
                BigDecimal.valueOf(discountAmount),
                BigDecimal.valueOf(10000),
                CouponCategory.FASHION,
                LocalDateTime.now().minusHours(1),
                LocalDateTime.now().plusHours(1)
        )).isInstanceOf(ConstraintViolationException.class)
                .hasMessageContainingAll("할인 금액", "500원 단위");
    }

    @ParameterizedTest
    @CsvSource({"1000,34000", "2000,9500"})
    @DisplayName("할인율이 3% 이상 20% 이하가 아닐 경우 예외가 발생한다.")
    void validateDiscountRate(long discountAmount, long minimumOrderAmount) {
        assertThatThrownBy(() -> new Coupon(
                "Jake",
                BigDecimal.valueOf(discountAmount),
                BigDecimal.valueOf(minimumOrderAmount),
                CouponCategory.FASHION,
                LocalDateTime.now().minusHours(1),
                LocalDateTime.now().plusHours(1)
        )).isInstanceOf(ConstraintViolationException.class)
                .hasMessageContainingAll("할인율", "3% 이상", "20% 이하");
    }

    @Test
    @DisplayName("발급 기간 시작일이 종료일 보다 이후일 경우 예외가 발생한다.")
    void validateIssuedStartDate() {
        LocalDateTime issuedStartDate = LocalDateTime.now().plusHours(1);
        assertThatThrownBy(() -> new Coupon(
                "Jake",
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(10000),
                CouponCategory.FASHION,
                issuedStartDate,
                issuedStartDate.minusNanos(1)
        )).isInstanceOf(ConstraintViolationException.class)
                .hasMessageContainingAll("발급 기간 시작일", "이전");
    }

    @Test
    @DisplayName("쿠폰 발급 가능 날짜가 동일하고 오늘인 경우 발급이 가능하다.")
    void issueAvailable() {
        // given
        LocalDateTime now = LocalDateTime.now();
        Coupon coupon = new Coupon(
                "Coupon",
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(10000),
                CouponCategory.FOOD,
                now,
                now
        );

        // when
        boolean actual = coupon.issueAvailable();

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("쿠폰 발급 가능 날짜가 동일하고 어제인 경우 발급이 불가능하다.")
    void issueUnavailable() {
        // given
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        Coupon coupon = new Coupon(
                "Coupon",
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(10000),
                CouponCategory.FOOD,
                yesterday,
                yesterday
        );

        // when
        boolean actual = coupon.issueAvailable();

        // then
        assertThat(actual).isFalse();
    }
}
