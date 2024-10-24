package coupon.domain.coupon;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    @Test
    @DisplayName("할인율을 퍼센트로 계산한다.")
    void calculateDiscountRate() {
        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(Calculator.calculateDiscountRateOfPercentage(19, 1000))
                .isEqualTo(1);
        softAssertions.assertThat(Calculator.calculateDiscountRateOfPercentage(20, 1000))
                .isEqualTo(2);

        softAssertions.assertAll();
    }
}
