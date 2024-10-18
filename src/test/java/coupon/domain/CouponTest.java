package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CouponTest {

    @ParameterizedTest
    @ValueSource(strings = {"", "0123465789012345678901234567891"})
    void 쿠폰의_이름은_최대_30글자이다(String name) {
        assertThatThrownBy(() -> new Coupon(name, 1000L, 30000L, Category.FOOD))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"12", "12345", "이것은_쿠폰"})
    void 쿠폰의_이름이_30자_이내인_경우_예외가_발생하지_않는다(String name) {
        assertThatNoException()
                .isThrownBy(() -> new Coupon(name, 1000L, 30000L, Category.FOOD));
    }

    @ParameterizedTest
    @ValueSource(ints = {1000, 1500})
    void 할인_금액은_1_000원_이상_10_000원_이하입니다(int discountAmount) {
        assertThatNoException()
                .isThrownBy(() -> new Coupon("coupon", discountAmount * 1L, 30000L, Category.FOOD));
    }

    @ParameterizedTest
    @ValueSource(ints = {1010, 1501})
    void 할인_금액은_500원_단위입니다(int discountAmount) {
        assertThatThrownBy(() -> new Coupon("coupon", discountAmount * 1L, 30000L, Category.FOOD))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {500, 10500})
    void 할인_금액은_1_000원_이상_10_000원_이하가_아니면_예외가_발생한다(int discountAmount) {
        assertThatThrownBy(() -> new Coupon("coupon", discountAmount * 1L, 30000L, Category.FOOD))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 시작일은_종료일보다_이전이어야_한다() {
        LocalDateTime issueStart = LocalDateTime.now();
        LocalDateTime issueEnd = issueStart.plusDays(1);
        assertThatNoException()
                .isThrownBy(() -> new Coupon("coupon", 1000L, 30000L, Category.FOOD, issueStart, issueEnd));
    }

    @Test
    void 시작일은_종료일보다_이전이_아니면_예외가_발생한다() {
        LocalDateTime issueStart = LocalDateTime.now();
        LocalDateTime issueEnd = issueStart.minusDays(1);
        assertThatThrownBy(() -> new Coupon("coupon", 1000L, 30000L, Category.FOOD, issueStart, issueEnd))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
