package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CouponTest {

    private CouponName COUPON_NAME = new CouponName("행운쿠폰");
    private IssuancePeriod ISSUANCE_PERIOD = new IssuancePeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1));

    @ParameterizedTest
    @CsvSource({"6500,30000", "2000,100000"})
    void 할인율이_3퍼센트_미만이거나_20퍼센트_초과이면_예외가_발생한다(long discountValue, long minOrderValue) {
        DiscountAmount discountAmount = new DiscountAmount(discountValue);
        MinOrderAmount minOrderAmount = new MinOrderAmount(minOrderValue);
        assertThatThrownBy(() -> new Coupon(COUPON_NAME, discountAmount, minOrderAmount, Category.FOOD, ISSUANCE_PERIOD));
    }
}
