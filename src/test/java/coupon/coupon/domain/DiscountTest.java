package coupon.coupon.domain;

import coupon.coupon.exception.CouponErrorMessage;
import coupon.coupon.exception.CouponException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DiscountTest {

    @Test
    void createDiscount_withValidPriceAndPercent_shouldSucceed() {
        // Given
        int validPrice = 5000;
        int minimumOrderAmount = 50000;
        int expectedPercent = 10;

        // When
        Discount discount = new Discount(validPrice, minimumOrderAmount);

        // Then
        assertEquals(validPrice, discount.getPrice());
        assertEquals(expectedPercent, discount.getPercent());
    }

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
}
