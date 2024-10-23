package coupon.domain.coupon;

import coupon.common.ErrorConstant;
import coupon.common.exception.CouponException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class

PriceInformationTest {

    @Test
    @DisplayName("총 할인율이 가능한 범위가 아닌 1% 인 경우 에러를 발생한다.")
    void priceInformation_WhenNoRate() {
        assertThatThrownBy(() -> new PriceInformation(DiscountPrice.MINIMUM_PRICE, MinimumPrice.MAXIMUM_PRICE))
                .isInstanceOf(CouponException.class)
                .hasMessage(ErrorConstant.COUPON_PRICE_RATE_NOT_IN_RANGE.getMessage());
    }

    @Test
    @DisplayName("총 할인율이 가능한 범위가 아닌 100% 인 경우 에러를 발생한다.")
    void priceInformation_When100Rate() {
        assertThatThrownBy(() -> new PriceInformation(DiscountPrice.MAXIMUM_PRICE, DiscountPrice.MAXIMUM_PRICE))
                .isInstanceOf(CouponException.class)
                .hasMessage(ErrorConstant.COUPON_PRICE_RATE_NOT_IN_RANGE.getMessage());
    }

    @Test
    @DisplayName("총 할인율이 가능한 범위인 경우 에러를 발생하지 않는다.")
    void priceInformation_WhenAvailableRate() {
        PriceInformation priceInformation = new PriceInformation(DiscountPrice.MINIMUM_PRICE, MinimumPrice.MINIMUM_PRICE);

        assertAll(
                () -> assertThat(priceInformation.getMinimumPrice()).isEqualTo(MinimumPrice.MINIMUM_PRICE),
                () -> assertThat(priceInformation.getDiscountPrice()).isEqualTo(DiscountPrice.MINIMUM_PRICE)
        );
    }
}
