package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CouponTest {

    @ParameterizedTest
    @CsvSource({"250,10000",
            "2100,10000"})
    @DisplayName("할인율은 3%이상 20%이하만 가능하다")
    void discountRate(int discount, int order) {
        assertThatThrownBy(
                () -> new Coupon("test", discount, order, null, LocalDate.now(), LocalDate.now())
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void name() {
        assertThatThrownBy(
                () -> new Coupon("", 1000, 10000, null, LocalDate.now(), LocalDate.now())
        ).isInstanceOf(IllegalArgumentException.class);

    }

    @ParameterizedTest
    @CsvSource({"700,10000",
            "1333,10000",
            "15000,150000"})
    void discount(int discount, int order) {
        assertThatThrownBy(
                () -> new Coupon("test", discount, order, null, LocalDate.now(), LocalDate.now())
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({"500,4000",
            "10000,100001"})
    void order(int discount, int order) {
        assertThatThrownBy(
                () -> new Coupon("test", discount, order, null, LocalDate.now(), LocalDate.now())
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void issuableDuration() {
        assertThatThrownBy(
                () -> new Coupon("test", 1000, 10000, null, LocalDate.now().plusDays(2), LocalDate.now())
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
