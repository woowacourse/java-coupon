package coupon.validator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.entity.Coupon;
import coupon.entity.CouponCategory;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CouponValidatorTest {

    private final String defaultName = "coupon";
    private final int defaultDiscountAmount = 1000;
    private final int defaultMinimumOrderAmount = 10000;
    private final CouponCategory defaultCategory = CouponCategory.FASHION;
    private final LocalDateTime defaultValidFrom = LocalDateTime.now();
    private final LocalDateTime defaultValidTo = LocalDateTime.now().plusDays(7);

    private CouponValidator couponValidator;

    @BeforeEach
    void setUp() {
        couponValidator = new CouponValidator();
    }

    @DisplayName("쿠폰 이름의 유효성을 확인한다.")
    @Nested
    class CouponNameTest {

        @DisplayName("이름은 Null일 수 없다.")
        @Test
        void nullName() {
            Coupon coupon =  Coupon.builder()
                    .name(null)
                    .discountAmount(defaultDiscountAmount)
                    .minimumOrderAmount(defaultMinimumOrderAmount)
                    .category(defaultCategory)
                    .validFrom(defaultValidFrom)
                    .validTo(defaultValidTo)
                    .build();
            assertThatThrownBy(() -> couponValidator.validate(coupon))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("이름은 빈 값일 수 없다.")
        @Test
        void emptyName() {
            Coupon coupon =  Coupon.builder()
                    .name("")
                    .discountAmount(defaultDiscountAmount)
                    .minimumOrderAmount(defaultMinimumOrderAmount)
                    .category(defaultCategory)
                    .validFrom(defaultValidFrom)
                    .validTo(defaultValidTo)
                    .build();
            assertThatThrownBy(() -> couponValidator.validate(coupon))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("이름은 30자 까지만 허용된다.")
        @Test
        void nameLength() {
            Coupon coupon =  Coupon.builder()
                    .name("1234567890123456789012345678901")
                    .discountAmount(defaultDiscountAmount)
                    .minimumOrderAmount(defaultMinimumOrderAmount)
                    .category(defaultCategory)
                    .validFrom(defaultValidFrom)
                    .validTo(defaultValidTo)
                    .build();
            assertThatThrownBy(() -> couponValidator.validate(coupon))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @DisplayName("쿠폰의 할인 금액과 최소 주문 금액의 유효성을 확인한다.")
    @Nested
    class AmountTest {

            @DisplayName("할인 금액은 1,000원 이상 10,000원 이하이다.")
            @ParameterizedTest
            @ValueSource(ints = {999, 10001})
            void invalidDiscountAmount(int discountAmount) {
                Coupon coupon =  Coupon.builder()
                        .name(defaultName)
                        .discountAmount(discountAmount)
                        .minimumOrderAmount(defaultMinimumOrderAmount)
                        .category(defaultCategory)
                        .validFrom(defaultValidFrom)
                        .validTo(defaultValidTo)
                        .build();

                assertThatThrownBy(() -> couponValidator.validate(coupon))
                        .isInstanceOf(IllegalArgumentException.class);
            }

            @DisplayName("할인 금액은 500원 단위여야 한다.")
            @Test
            void invalidDiscountAmountUnit() {
                Coupon coupon =  Coupon.builder()
                        .name(defaultName)
                        .discountAmount(1001)
                        .minimumOrderAmount(defaultMinimumOrderAmount)
                        .category(defaultCategory)
                        .validFrom(defaultValidFrom)
                        .validTo(defaultValidTo)
                        .build();

                assertThatThrownBy(() -> couponValidator.validate(coupon))
                        .isInstanceOf(IllegalArgumentException.class);
            }

            @DisplayName("최소 주문 금액은 5,000원 이상 100,000원 이하이다.")
            @ParameterizedTest
            @ValueSource(ints = {4999, 100001})
            void invalidMinimumOrderAmount(int minimumOrderAmount) {
                Coupon coupon =  Coupon.builder()
                        .name(defaultName)
                        .discountAmount(defaultDiscountAmount)
                        .minimumOrderAmount(minimumOrderAmount)
                        .category(defaultCategory)
                        .validFrom(defaultValidFrom)
                        .validTo(defaultValidTo)
                        .build();

                assertThatThrownBy(() -> couponValidator.validate(coupon))
                        .isInstanceOf(IllegalArgumentException.class);
            }
    }

    @DisplayName("쿠폰의 카테고리 유효성을 확인한다.")
    @Nested
    class CategoryTest {

        @DisplayName("카테고리는 Null일 수 없다.")
        @Test
        void nullCategory() {
            Coupon coupon =  Coupon.builder()
                    .name(defaultName)
                    .discountAmount(defaultDiscountAmount)
                    .minimumOrderAmount(defaultMinimumOrderAmount)
                    .category(null)
                    .validFrom(defaultValidFrom)
                    .validTo(defaultValidTo)
                    .build();


            assertThatThrownBy(() -> couponValidator.validate(coupon))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @DisplayName("쿠폰 기간의 유효성을 확인한다.")
    @Nested
    class ValidDateTest {

        @DisplayName("시작일은 null일 수 없다.")
        @Test
        void nullValidFrom() {
            Coupon coupon =  Coupon.builder()
                    .name(defaultName)
                    .discountAmount(defaultDiscountAmount)
                    .minimumOrderAmount(defaultMinimumOrderAmount)
                    .category(defaultCategory)
                    .validFrom(null)
                    .validTo(defaultValidTo)
                    .build();

            assertThatThrownBy(() -> couponValidator.validate(coupon))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("종료일은 null일 수 없다.")
        @Test
        void nullValidTo() {
            Coupon coupon =  Coupon.builder()
                    .name(defaultName)
                    .discountAmount(defaultDiscountAmount)
                    .minimumOrderAmount(defaultMinimumOrderAmount)
                    .category(defaultCategory)
                    .validFrom(defaultValidFrom)
                    .validTo(null)
                    .build();

            assertThatThrownBy(() -> couponValidator.validate(coupon))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("시작일은 종료일 이전이어야 한다.")
        @Test
        void invalidDate() {
            Coupon coupon =  Coupon.builder()
                    .name(defaultName)
                    .discountAmount(defaultDiscountAmount)
                    .minimumOrderAmount(defaultMinimumOrderAmount)
                    .category(defaultCategory)
                    .validFrom(defaultValidFrom.plusDays(1))
                    .validTo(defaultValidFrom)
                    .build();

            assertThatThrownBy(() -> couponValidator.validate(coupon))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
