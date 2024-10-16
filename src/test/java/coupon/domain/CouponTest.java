package coupon.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class CouponTest {

    @Test
    void testValidCouponCreation() {
        String name = "Spring Sale";
        int discountAmount = 3_000;
        int minOrderAmount = 20_000;
        String category = "패션";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(10);

        Coupon coupon = new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate);

        assertAll(
                () -> assertThat(coupon.getName()).isEqualTo(name),
                () -> assertThat(coupon.getDiscountAmount()).isEqualTo(discountAmount),
                () -> assertThat(coupon.getMinOrderAmount()).isEqualTo(minOrderAmount),
                () -> assertThat(coupon.getCategory()).isEqualTo(CouponCategory.from(category)),
                () -> assertThat(coupon.getStartDate()).isEqualTo(startDate),
                () -> assertThat(coupon.getEndDate()).isEqualTo(endDate)
        );
    }

    @Test
    void testNameMustExist() {
        String name = null;
        int discountAmount = 3_000;
        int minOrderAmount = 20_000;
        String category = "패션";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(10);

        assertThatThrownBy(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name must exist and not be empty.");
    }

    @Test
    void testNameMustNotBeEmpty() {
        String name = "   ";
        int discountAmount = 3_000;
        int minOrderAmount = 20_000;
        String category = "패션";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(10);

        assertThatThrownBy(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name must exist and not be empty.");
    }

    @Test
    void testNameLengthExceedsMaximum() {
        String name = "This coupon name is definitely more than thirty characters long";
        int discountAmount = 3_000;
        int minOrderAmount = 20_000;
        String category = "패션";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(10);

        assertThatThrownBy(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name length must be 30 characters or less.");
    }

    @Test
    void testDiscountAmountBelowMinimum() {
        String name = "Sale";
        int discountAmount = 500;
        int minOrderAmount = 20_000;
        String category = "가전";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);

        assertThatThrownBy(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Discount amount must be at least 1000 won.");
    }

    @Test
    void testDiscountAmountAboveMaximum() {
        String name = "Sale";
        int discountAmount = 15_000;
        int minOrderAmount = 20_000;
        String category = "가구";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);

        assertThatThrownBy(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Discount amount must be at most 10000 won.");
    }

    @Test
    void testDiscountAmountNotIncrementOf500() {
        String name = "Sale";
        int discountAmount = 2_750;
        int minOrderAmount = 20_000;
        String category = "식품";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);

        assertThatThrownBy(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Discount amount must be in increments of 500 won.");
    }

    @Test
    void testMinOrderAmountBelowMinimum() {
        String name = "Sale";
        int discountAmount = 5_000;
        int minOrderAmount = 3_000;
        String category = "패션";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);

        assertThatThrownBy(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Minimum order amount must be at least 5000 won.");
    }

    @Test
    void testMinOrderAmountAboveMaximum() {
        String name = "Sale";
        int discountAmount = 5_000;
        int minOrderAmount = 150_000;
        String category = "가전";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);

        assertThatThrownBy(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Minimum order amount must be at most 100000 won.");
    }

    @Test
    void testDiscountRateBelowMinimum() {
        String name = "Sale";
        int discountAmount = 1_000;
        int minOrderAmount = 50_000;
        String category = "가구";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);

        assertThatThrownBy(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Discount rate must be at least 3%.");
    }

    @Test
    void testDiscountRateAboveMaximum() {
        String name = "Sale";
        int discountAmount = 2_500;
        int minOrderAmount = 10_000;
        String category = "식품";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);

        assertThatThrownBy(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Discount rate must be at most 20%.");
    }

    @Test
    void testInvalidCategory() {
        String name = "Sale";
        int discountAmount = 3_000;
        int minOrderAmount = 20_000;
        String category = "InvalidCategory";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);

        assertThatThrownBy(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid category.");
    }

    @Test
    void testStartDateAfterEndDate() {
        String name = "Sale";
        int discountAmount = 3_000;
        int minOrderAmount = 20_000;
        String category = "패션";
        LocalDate startDate = LocalDate.now().plusDays(5);
        LocalDate endDate = LocalDate.now();

        assertThatThrownBy(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Start date must be before or equal to end date.");
    }

    @Test
    void testStartDateAndEndDateAreEqual() {
        String name = "One Day Sale";
        int discountAmount = 3_000;
        int minOrderAmount = 20_000;
        String category = "가전";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now(); // Same as start date

        Coupon coupon = new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate);

        assertAll(
                () -> assertThat(coupon.getStartDate()).isEqualTo(startDate),
                () -> assertThat(coupon.getEndDate()).isEqualTo(endDate)
        );
    }

    @Test
    void testNullStartDate() {
        String name = "Sale";
        int discountAmount = 3_000;
        int minOrderAmount = 20_000;
        String category = "가구";
        LocalDate startDate = null;
        LocalDate endDate = LocalDate.now().plusDays(5);

        assertThatThrownBy(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Start date and end date must not be null.");
    }

    @Test
    void testNullEndDate() {
        String name = "Sale";
        int discountAmount = 3_000;
        int minOrderAmount = 20_000;
        String category = "식품";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = null;

        assertThatThrownBy(() -> new Coupon(name, discountAmount, minOrderAmount, category, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Start date and end date must not be null.");
    }
}
