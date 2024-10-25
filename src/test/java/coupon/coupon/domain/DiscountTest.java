package coupon.coupon.domain;

import coupon.coupon.exception.CouponErrorMessage;
import coupon.coupon.exception.CouponException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscountTest {

    @DisplayName("올바른 가격과 할인율로 할인 객체를 생성한다.")
    @Test
    void createDiscount_withValidPriceAndPercent_shouldSucceed() {
        // Given
        int validPrice = 5000;
        int minimumOrderAmount = 50000;
        int expectedPercent = 10;

        // When
        Discount discount = new Discount(validPrice, minimumOrderAmount);

        // Then
        assertAll(
                () -> assertEquals(validPrice, discount.getPrice()),
                () -> assertEquals(expectedPercent, discount.getPercent())
        );
    }

    @DisplayName("최소 할인 금액보다 작은 경우 실패한다.")
    @Test
    void createDiscount_withPriceLowerThanMinimum_shouldFail() {
        // Given
        int invalidPrice = 500;
        int minimumOrderAmount = 10000;

        // When & Then
        CouponException exception = assertThrows(CouponException.class, () -> {
            new Discount(invalidPrice, minimumOrderAmount);
        });

        assertEquals(CouponErrorMessage.INVALID_DISCOUNT_PRICE, exception.getErrorMessage());
    }

    @DisplayName("최대 할인 금액보다 큰 경우 실패한다.")
    @Test
    void createDiscount_withPriceHigherThanMaximum_shouldFail() {
        // Given
        int invalidPrice = 15000;
        int minimumOrderAmount = 50000;

        // When & Then
        CouponException exception = assertThrows(CouponException.class, () -> {
            new Discount(invalidPrice, minimumOrderAmount);
        });

        assertEquals(CouponErrorMessage.INVALID_DISCOUNT_PRICE, exception.getErrorMessage());
    }

    @DisplayName("할인 금액 단위로 나누어지지 않은 경우 실패한다.")
    @Test
    void createDiscount_withPriceNotMultipleOfUnit_shouldFail() {
        // Given
        int invalidPrice = 550;
        int minimumOrderAmount = 10000;

        // When & Then
        CouponException exception = assertThrows(CouponException.class, () -> {
            new Discount(invalidPrice, minimumOrderAmount);
        });

        assertEquals(CouponErrorMessage.INVALID_DISCOUNT_PRICE, exception.getErrorMessage());
    }

    @DisplayName("할인율이 최솟값보다 작은 경우 예외처리한다.")
    @Test
    void createDiscount_withPercentLowerThanMinimum_shouldFail() {
        // Given
        int price = 2000;
        int minimumOrderAmount = 100000;

        // When & Then
        CouponException exception = assertThrows(CouponException.class, () -> {
            new Discount(price, minimumOrderAmount);
        });

        assertEquals(CouponErrorMessage.INVALID_DISCOUNT_PERCENT, exception.getErrorMessage());
    }

    @DisplayName("할인율이 최댓값보다 큰 경우 예외처리한다.")
    @Test
    void createDiscount_withPercentHigherThanMaximum_shouldFail() {
        // Given
        int price = 9000;
        int minimumOrderAmount = 10000;

        // When & Then
        CouponException exception = assertThrows(CouponException.class, () -> {
            new Discount(price, minimumOrderAmount);
        });

        assertEquals(CouponErrorMessage.INVALID_DISCOUNT_PERCENT, exception.getErrorMessage());
    }

    @Test
    @DisplayName("할인 금액이 1000원이고, 최소 주문 금액이 6000원인 경우 할인율은 0.16이다. ")
    void createDiscount_withPriceAndMinimumOrderAmount() {
        // Given
        int validPrice = 1000;
        int minimumOrderAmount = 6000;
        int expectedPercent = 16;

        // When
        Discount discount = new Discount(validPrice, minimumOrderAmount);

        // Then
        assertAll(
                () -> assertEquals(validPrice, discount.getPrice()),
                () -> assertEquals(expectedPercent, discount.getPercent())
        );
    }
}
