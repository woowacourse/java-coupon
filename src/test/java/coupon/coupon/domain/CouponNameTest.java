package coupon.coupon.domain;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import coupon.coupon.CouponException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CouponNameTest {

    @DisplayName("이름이 없으면 예외가 발생한다.")
    @Test
    void cannotCreateIfNoName() {
        // given
        int discountAmount = 1000;
        int minimumOrderAmount = 5000;
        Category category = Category.FASHION;
        LocalDate startAt = LocalDate.now();
        LocalDate endAt = LocalDate.now().plusDays(1);

        // when
        assertThatThrownBy(() -> new Coupon(null, discountAmount, minimumOrderAmount, category, startAt, endAt))
                .isInstanceOf(CouponException.class)
                .hasMessage("쿠폰 이름이 누락되었습니다.");
    }

    @DisplayName("이름이 30자를 초과하면 예외가 발생한다.")
    @Test
    void cannotCreateIfExceedLength() {
        // given
        String name = "가".repeat(31);
        int discountAmount = 1000;
        int minimumOrderAmount = 5000;
        Category category = Category.FASHION;
        LocalDate startAt = LocalDate.now();
        LocalDate endAt = LocalDate.now().plusDays(1);

        // when & then
        assertThatThrownBy(() -> new Coupon(name, discountAmount, minimumOrderAmount, category, startAt, endAt))
                .isInstanceOf(CouponException.class)
                .hasMessage("쿠폰은 30자 이하의 이름을 설정해주세요.");
    }
}
