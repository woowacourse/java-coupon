package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.exception.CouponConditionException;
import java.time.LocalDate;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CouponTest {

    @Nested
    class ValidateTest {

        private static final String NAME = "default_name";
        private static final int MIN_ORDER_AMOUNT = 50_000;
        private static final int DISCOUNT_AMOUNT = 5_000;
        private static final Category CATEGORY = Category.FASHION;
        private static final LocalDate START_ISSUE_DATE = LocalDate.of(2024, 12, 1);
        private static final LocalDate END_ISSUE_DATE = LocalDate.of(2024, 12, 10);

        @Nested
        class NameTest {

            private static final String EXCEPTION_MESSAGE = "Coupon name must not be null and must be less than limit.";

            @ParameterizedTest
            @NullAndEmptySource
            void whenNameIsEmpty_throwException(String name) {
                assertThatThrownBy(() -> new Coupon(name, DISCOUNT_AMOUNT, MIN_ORDER_AMOUNT, CATEGORY, START_ISSUE_DATE,
                        END_ISSUE_DATE))
                        .isInstanceOf(CouponConditionException.class)
                        .hasMessage(EXCEPTION_MESSAGE);
            }

            @Test
            void whenNameIsOverLimit_throwException() {
                String name = "a".repeat(31);

                assertThatThrownBy(() -> new Coupon(name, DISCOUNT_AMOUNT, MIN_ORDER_AMOUNT, CATEGORY, START_ISSUE_DATE,
                        END_ISSUE_DATE))
                        .isInstanceOf(CouponConditionException.class)
                        .hasMessage(EXCEPTION_MESSAGE);
            }

            @ParameterizedTest
            @ValueSource(ints = {1, 30})
            void whenNameLengthIsInbound(int length) {
                String name = "a".repeat(length);

                assertThatCode(() -> new Coupon(name, DISCOUNT_AMOUNT, MIN_ORDER_AMOUNT, CATEGORY, START_ISSUE_DATE,
                        END_ISSUE_DATE))
                        .doesNotThrowAnyException();
            }
        }

        @Nested
        class MinOrderAmountTest {

            private static final String EXCEPTION_MESSAGE = "Minimum order amount must be in bound.";

            @ParameterizedTest
            @ValueSource(ints = {4_999, 100_001})
            void whenMinOrderAmountOutOfBounds_throwException(int minOrderAmount) {
                assertThatThrownBy(() -> new Coupon(NAME, DISCOUNT_AMOUNT, minOrderAmount, CATEGORY, START_ISSUE_DATE,
                        END_ISSUE_DATE))
                        .isInstanceOf(CouponConditionException.class)
                        .hasMessage(EXCEPTION_MESSAGE);
            }

            @ParameterizedTest
            @CsvSource({"5_000, 20", "100_000, 5"})
            void whenMinOrderAmountIsInBounds(int minOrderAmount, int discountPercentage) {
                int discountAmount = minOrderAmount / 100 * discountPercentage;

                assertThatCode(() -> new Coupon(NAME, discountAmount, minOrderAmount, CATEGORY, START_ISSUE_DATE,
                        END_ISSUE_DATE))
                        .doesNotThrowAnyException();
            }
        }

        @Nested
        class DiscountAmountTest {

            private static final String AMOUNT_EXCEPTION_MESSAGE = "Discount amount must be in bound and multiple of unit.";
            private static final String RATE_EXCEPTION_MESSAGE = "Discount rate must be in bound.";

            @ParameterizedTest
            @ValueSource(ints = {10_500, 9_999, 5_501})
            void whenDiscountAmountOutOfBoundsOrInvalidUnit_throwException(int discountAmount) {
                assertThatThrownBy(() -> new Coupon(NAME, discountAmount, MIN_ORDER_AMOUNT, CATEGORY, START_ISSUE_DATE,
                        END_ISSUE_DATE))
                        .isInstanceOf(CouponConditionException.class)
                        .hasMessage(AMOUNT_EXCEPTION_MESSAGE);
            }

            @ParameterizedTest
            @ValueSource(ints = {10_000, 5_500})
            void whenDiscountAmountIsValid(int discountAmount) {
                assertThatCode(() -> new Coupon(NAME, discountAmount, MIN_ORDER_AMOUNT, CATEGORY, START_ISSUE_DATE,
                        END_ISSUE_DATE))
                        .doesNotThrowAnyException();
            }

            @ParameterizedTest
            @CsvSource({"2_500, 100_000", "2_500, 10_000"})
            void whenDiscountRateOutOfBounds_throwException(int discountAmount, int minOrderAmount) {
                assertThatThrownBy(() -> new Coupon(NAME, discountAmount, minOrderAmount, CATEGORY, START_ISSUE_DATE,
                        END_ISSUE_DATE))
                        .isInstanceOf(CouponConditionException.class)
                        .hasMessage(RATE_EXCEPTION_MESSAGE);
            }

            @ParameterizedTest
            @CsvSource({"3_000, 100_000", "2_000, 10_000"})
            void whenDiscountRateIsValid(int discountAmount, int minOrderAmount) {
                assertThatCode(() -> new Coupon(NAME, discountAmount, minOrderAmount, CATEGORY, START_ISSUE_DATE,
                        END_ISSUE_DATE))
                        .doesNotThrowAnyException();
            }
        }

        @Nested
        class CategoryTest {

            private static final String EXCEPTION_MESSAGE = "Category must not be null.";

            @Test
            void whenCategoryIsNull_throwException() {
                assertThatThrownBy(() -> new Coupon(NAME, DISCOUNT_AMOUNT, MIN_ORDER_AMOUNT, null, START_ISSUE_DATE,
                        END_ISSUE_DATE))
                        .isInstanceOf(CouponConditionException.class)
                        .hasMessage(EXCEPTION_MESSAGE);
            }

            @Test
            void whenCategoryIsValid() {
                assertThatCode(() -> new Coupon(NAME, DISCOUNT_AMOUNT, MIN_ORDER_AMOUNT, CATEGORY, START_ISSUE_DATE,
                        END_ISSUE_DATE))
                        .doesNotThrowAnyException();
            }
        }

        @Nested
        class IssueDateTest {

            private static final String EXCEPTION_MESSAGE = "Start issue date must be before or equal to end issue date.";

            @Test
            void whenStartIssueDateIsAfterEndIssueDate_throwException() {
                LocalDate startIssueDate = LocalDate.of(2024, 12, 11);
                LocalDate endIssueDate = LocalDate.of(2024, 12, 10);

                assertThatThrownBy(() -> new Coupon(NAME, DISCOUNT_AMOUNT, MIN_ORDER_AMOUNT, CATEGORY, startIssueDate,
                        endIssueDate))
                        .isInstanceOf(CouponConditionException.class)
                        .hasMessage(EXCEPTION_MESSAGE);
            }

            @Test
            void whenStartIssueDateIsBeforeOrEqualToEndIssueDate() {
                LocalDate startIssueDate = LocalDate.of(2024, 12, 1);
                LocalDate endIssueDate = LocalDate.of(2024, 12, 10);

                assertThatCode(() -> new Coupon(NAME, DISCOUNT_AMOUNT, MIN_ORDER_AMOUNT, CATEGORY, startIssueDate,
                        endIssueDate))
                        .doesNotThrowAnyException();
            }
        }
    }
}
