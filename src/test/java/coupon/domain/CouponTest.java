package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.vo.DiscountAmount;
import coupon.domain.vo.IssuePeriod;
import coupon.domain.vo.MinimumOrderPrice;
import coupon.domain.vo.Name;
import coupon.exception.ErrorMessage;
import coupon.exception.GlobalCustomException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CouponTest {

    @ParameterizedTest
    @CsvSource({
            "2500, 5000",
            "1000, 100000"
    })
    @DisplayName("쿠폰의 할인율은 3% 이상 20% 이하다.")
    void validateDiscountRate(int invalidDiscountAmount, int invalidMinimumOrderPrice) {
        Name name = new Name("쿠폰이름");
        DiscountAmount discountAmount = new DiscountAmount(invalidDiscountAmount);
        MinimumOrderPrice minimumOrderPrice = new MinimumOrderPrice(invalidMinimumOrderPrice);
        IssuePeriod issuePeriod = new IssuePeriod(LocalDateTime.now(), LocalDateTime.now());

        assertThatThrownBy(
                () -> new Coupon(name, discountAmount, minimumOrderPrice, Category.FASHION, issuePeriod))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.INVALID_DISCOUNT_RATE_RANGE.getMessage());
    }
}
