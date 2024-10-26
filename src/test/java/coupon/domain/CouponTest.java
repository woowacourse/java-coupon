package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CouponTest {

    @Nested
    @DisplayName("생성 테스트")
    class ConstructorTest {

        @Test
        @DisplayName("정상 생성")
        void success() {
            assertThatCode(
                    () -> new Coupon("name", 1000, 10000, Category.FASHION, LocalDate.now(), LocalDate.now()))
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("할인율이 3% 미만이면 쿠폰을 생성할 수 없다.")
        void failWithTooSmallDiscount() {
            assertThatThrownBy(
                    () -> new Coupon("name", 2000, 100000, Category.FASHION, LocalDate.now(), LocalDate.now()))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("할인율이 20%를 초과하면 쿠폰을 생성할 수 없다.")
        void failWithTooBigDiscount() {
            assertThatThrownBy(
                    () -> new Coupon("name", 2100, 10000, Category.FASHION, LocalDate.now(), LocalDate.now()))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
