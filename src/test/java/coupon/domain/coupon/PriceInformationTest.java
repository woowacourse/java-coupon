package coupon.domain.coupon;

import coupon.common.ErrorConstant;
import coupon.common.exception.CouponException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PriceInformationTest {

    @Test
    @DisplayName("총 할인율이 가능한 범위가 아닌 1% 인 경우 에러를 발생한다.")
    void priceInformation_WhenNoRate() {
        assertThatThrownBy(() -> new PriceInformation(1000, 100000))
                .isInstanceOf(CouponException.class)
                .hasMessage(ErrorConstant.COUPON_PRICE_RATE_NOT_IN_RANGE.getMessage());
    }

    @Test
    @DisplayName("총 할인율이 가능한 범위가 아닌 100% 인 경우 에러를 발생한다.")
    void priceInformation_When100Rate() {
        assertThatThrownBy(() -> new PriceInformation(10000, 10000))
                .isInstanceOf(CouponException.class)
                .hasMessage(ErrorConstant.COUPON_PRICE_RATE_NOT_IN_RANGE.getMessage());
    }
}
