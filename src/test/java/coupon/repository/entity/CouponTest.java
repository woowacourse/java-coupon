package coupon.repository.entity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import coupon.domain.coupon.ProductCategory;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class CouponTest {

    private final int discountAmount = 5_000;
    private final int minOrderAmount = 50_000;
    private final ProductCategory productCategory = ProductCategory.패션;
    private final LocalDateTime now = LocalDateTime.now();

    @Test
    void 예외_이름길이가_30초과일때() {
        // given
        String name = "0123456789012345678901234567890123456789";

        // when & then
        assertThatThrownBy(
                () -> new Coupon(name, discountAmount, minOrderAmount, productCategory, now, now.plusDays(6)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 이름의 길이는 30 초과일 수 없습니다: " + name);

    }

    @Test
    void 성공_쿠폰생성() {
        // given
        String name = "ever";

        // when & then
        assertDoesNotThrow(
                () -> new Coupon(name, discountAmount, minOrderAmount, productCategory, now, now.plusDays(6)));

    }
}
