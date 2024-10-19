package coupon.domain.coupon;

import coupon.common.ErrorConstant;
import coupon.common.exception.CouponException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiscountPriceTest {

    @Test
    @DisplayName("쿠폰의 할인 가격이 범위보다 작을 경우 에러를 발생한다.")
    void discountPrice_NotInRange_Small() {
        assertThatThrownBy(() -> new DiscountPrice(DiscountPrice.MINIMUM_PRICE - 1))
                .isInstanceOf(CouponException.class)
                .hasMessage(ErrorConstant.DISCOUNT_PRICE_NOT_IN_RANGE.getMessage());
    }

    @Test
    @DisplayName("쿠폰의 할인 가격이 범위보다 클 경우 에러를 발생한다.")
    void discountPrice_NotInRange_Big() {
        assertThatThrownBy(() -> new DiscountPrice(DiscountPrice.MAXIMUM_PRICE + 1))
                .isInstanceOf(CouponException.class)
                .hasMessage(ErrorConstant.DISCOUNT_PRICE_NOT_IN_RANGE.getMessage());
    }

    @Test
    @DisplayName("쿠폰의 가격 단위에 맞지 않는 경우 에러를 발생한다.")
    void discountPrice_NotAvailableUnit() {
        assertThatThrownBy(() -> new DiscountPrice(DiscountPrice.MINIMUM_PRICE + 1))
                .isInstanceOf(CouponException.class)
                .hasMessage(ErrorConstant.NOT_AVAILABLE_UNIT_PRICE.getMessage());
    }
}
