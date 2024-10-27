package coupon.domain.coupon;

import static java.beans.Beans.isInstanceOf;
import static org.junit.jupiter.api.Assertions.*;

import coupon.exception.CouponConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;

class AccountingTest {

    @DisplayName("할인 금액이 너무 적거나 많으면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(ints = {-1, 999, 10001, 999999})
    void getDiscountRate(int discount) {
        Assertions.assertThatThrownBy(() -> new Accounting(discount, 5000))
                .isInstanceOf(CouponConstraintViolationException.class)
                .hasMessageContaining("discount")
                .hasMessageContaining("out of bound");
    }

    @DisplayName("최소 주문 금액이 너무 적거나 많으면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(ints = {-1, 999, 4999, 100001})
    void getOrderCost(int order) {
        Assertions.assertThatThrownBy(() -> new Accounting(10000, order))
                .isInstanceOf(CouponConstraintViolationException.class)
                .hasMessageContaining("order")
                .hasMessageContaining("out of bound");
    }

    @DisplayName("최소 금액 단위와 다르게 설정되어 있다면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(ints = {1001, 5432, 9998})
    void discountUnit(int discount) {
        Assertions.assertThatThrownBy(() -> new Accounting(discount, 5000))
                .isInstanceOf(CouponConstraintViolationException.class)
                .hasMessageContaining("discount unit");
    }

    @DisplayName("할인율 기준보다 적거나 많으면 예외가 발생한다")
    @ParameterizedTest
    @CsvSource(value = {"2000,100000", "10000,5000"})
    void discountRate(int discount, int order) {
        Assertions.assertThatThrownBy(() -> new Accounting(discount, order))
                .isInstanceOf(CouponConstraintViolationException.class)
                .hasMessageContaining("discount rate")
                .hasMessageContaining("out of bound");
    }
}
