package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class DiscountAmountTest {

    @ParameterizedTest
    @NullSource
    @DisplayName("할인 금액 생성 실패: 할인 금액이 Null 인 경우")
    void createName(Long invalidAmount) {
        assertThatThrownBy(() -> new DiscountAmount(invalidAmount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 null이 될 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(longs = {999, 10_001})
    @DisplayName("할인 금액 생성 실패: 할인 금액의 범위가 조건에 맞지 않는 경우")
    void createAmountWhenInvalidRange(final Long amount) {
        assertThatThrownBy(() -> new DiscountAmount(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 1,000원 이상 10,000원 이하만 가능합니다.");
    }

    @Test
    @DisplayName("할인 금액 생성 실패: 할인 금액의 단위가 조건에 맞지 않는 경우")
    void createAmountWhenInvalidUnit() {
        assertThatThrownBy(() -> new DiscountAmount(1_100L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인 금액은 500원 단위로만 가능합니다.");
    }
}
