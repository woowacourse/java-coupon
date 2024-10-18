package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountAmountTest {

    @ParameterizedTest
    @ValueSource(ints = {500, 999, 10001})
    void 할인_금액이_1000원_이상_10000원_이하가_아니면_예외가_발생한다(long discountAmount) {
        assertThatThrownBy(() -> new DiscountAmount(discountAmount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {1001, 1})
    void 할인_금액이_500원_단위가_아니면_예외가_발생한다(long discountAmount) {
        assertThatThrownBy(() -> new DiscountAmount(discountAmount))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

