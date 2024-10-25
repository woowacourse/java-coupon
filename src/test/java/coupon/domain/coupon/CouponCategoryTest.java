package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.exception.CouponException;
import coupon.exception.ExceptionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponCategoryTest {

    @DisplayName("존재하지 않는 카테고리이면 예외가 발생한다.")
    @Test
    void getCategoryWithNotFound() {
        String given = "존재하지않는 카테고리";

        assertThatThrownBy(() -> CouponCategory.getCategory(given))
                .isInstanceOf(CouponException.class)
                .hasMessage(ExceptionType.COUPON_CATEGORY_NOT_FOUND.getMessage());
    }
}
