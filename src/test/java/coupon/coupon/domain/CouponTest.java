package coupon.coupon.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import coupon.CouponException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CouponTest {

    @DisplayName("쿠폰을 생성한다.")
    @Test
    void createCoupon() {
        // given
        String name = "coupon";
        int discountAmount = 1000;
        int minimumOrderAmount = 30000;
        Category category = Category.FASHION;
        LocalDate startAt = LocalDate.now();
        LocalDate endAt = LocalDate.now().plusDays(1);

        // when & then
        assertThatNoException().isThrownBy(() -> new Coupon(name, discountAmount, minimumOrderAmount, category, startAt, endAt));
    }

    @DisplayName("할인율이 3% 미만이면 예외가 발생한다.")
    @Test
    void cannotCreateIfDiscountRateUnder() {
        // given
        String name = "coupon";
        int discountAmount = 1000;
        int minimumOrderAmount = 50000;
        Category category = Category.FASHION;
        LocalDate startAt = LocalDate.now();
        LocalDate endAt = LocalDate.now().plusDays(1);

        // when & then
        assertThatThrownBy(() -> new Coupon(name, discountAmount, minimumOrderAmount, category, startAt, endAt))
                .isInstanceOf(CouponException.class)
                .hasMessage("할인율은 3% 이상, 20% 이하이어야 합니다.");
    }

    @DisplayName("할인율이 20% 초과이면 예외가 발생한다.")
    @Test
    void cannotCreateIfDiscountRateOver() {
        // given
        String name = "coupon";
        int discountAmount = 2500;
        int minimumOrderAmount = 10000;
        Category category = Category.FASHION;
        LocalDate startAt = LocalDate.now();
        LocalDate endAt = LocalDate.now().plusDays(1);

        // when & then
        assertThatThrownBy(() -> new Coupon(name, discountAmount, minimumOrderAmount, category, startAt, endAt))
                .isInstanceOf(CouponException.class)
                .hasMessage("할인율은 3% 이상, 20% 이하이어야 합니다.");
    }

    @DisplayName("카테고리는 null일 수 없다.")
    @Test
    void cannotCreateIfCategoryNull() {
        // given
        String name = "coupon";
        int discountAmount = 2000;
        int minimumOrderAmount = 10000;
        Category category = null;
        LocalDate startAt = LocalDate.now();
        LocalDate endAt = LocalDate.now().plusDays(1);

        // when & then
        assertThatThrownBy(() -> new Coupon(name, discountAmount, minimumOrderAmount, category, startAt, endAt))
                .isInstanceOf(CouponException.class)
                .hasMessage("카테고리를 선택해주세요.");
    }

    @DisplayName("주어진 날짜에 쿠폰을 발급할 수 없으면 참을 반환한다.")
    @Test
    void unableToIssue() {
        // given
        String name = "coupon";
        int discountAmount = 2000;
        int minimumOrderAmount = 10000;
        Category category = Category.FASHION;
        LocalDateTime issuedAt = LocalDateTime.now();
        LocalDate startAt = LocalDate.now().plusDays(1);
        LocalDate endAt = LocalDate.now().plusDays(2);
        Coupon coupon = new Coupon(name, discountAmount, minimumOrderAmount, category, startAt, endAt);

        // when
        boolean result = coupon.isUnableToIssue(issuedAt);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("주어진 날짜에 쿠폰을 발급할 수 없으면 참을 반환한다.")
    @Test
    void ableToIssue() {
        // given
        String name = "coupon";
        int discountAmount = 2000;
        int minimumOrderAmount = 10000;
        Category category = Category.FASHION;
        LocalDateTime issuedAt = LocalDateTime.now();
        LocalDate startAt = LocalDate.now();
        LocalDate endAt = LocalDate.now().plusDays(2);
        Coupon coupon = new Coupon(name, discountAmount, minimumOrderAmount, category, startAt, endAt);

        // when
        boolean result = coupon.isUnableToIssue(issuedAt);

        // then
        assertThat(result).isFalse();
    }
}
