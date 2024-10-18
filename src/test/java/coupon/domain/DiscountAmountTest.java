package coupon.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class DiscountAmountTest {

    @ParameterizedTest
    @ValueSource(ints = {1000, 10000})
    @DisplayName("할인 금액은 1,000원 이상 ~ 10,000원 이하여야 한다.")
    void validDiscountAmount(int amount) {
        DiscountAmount discountAmount = new DiscountAmount(amount);

        assertThat(discountAmount).isEqualTo(new DiscountAmount(amount));
    }
}
