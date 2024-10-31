package coupon.coupon.domain;

import coupon.coupon.exception.CouponException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class CouponTest {

    @Test
    @DisplayName("유효한 입력 값으로 Coupon 객체를 생성할 수 있다.")
    void createCouponWithValidValues() {
        // Given
        String name = "Welcome Discount";
        int price = 1500;
        int minimumOrderAmount = 10000;
        Category category = Category.FOOD;
        LocalDateTime startAt = LocalDateTime.now();
        LocalDateTime endAt = startAt.plusDays(10);

        // When
        Coupon coupon = new Coupon(name, price, minimumOrderAmount, category, startAt, endAt);

        // Then
        assertAll(
                () -> assertThat(coupon.getName()).isEqualTo(name),
                () -> assertThat(coupon.getDiscount().getPrice()).isEqualTo(price),
                () -> assertThat(coupon.getMinimumOrderAmount()).isEqualTo(minimumOrderAmount),
                () -> assertThat(coupon.getCategory()).isEqualTo(category),
                () -> assertThat(coupon.getStartAt()).isEqualTo(startAt),
                () -> assertThat(coupon.getEndAt()).isEqualTo(endAt)
        );
    }

    @Test
    @DisplayName("이름이 빈 값이면 예외가 발생한다.")
    void createCouponWithEmptyName() {
        // Given
        String name = "";
        int price = 1500;
        int minimumOrderAmount = 10000;
        Category category = Category.FOOD;
        LocalDateTime startAt = LocalDateTime.now();
        LocalDateTime endAt = startAt.plusDays(10);

        // When & Then
        assertThatThrownBy(() -> new Coupon(name, price, minimumOrderAmount, category, startAt, endAt))
                .isInstanceOf(CouponException.class);
    }

    @Test
    @DisplayName("이름이 너무 길면 예외가 발생한다.")
    void createCouponWithLongName() {
        // Given
        String name = "A".repeat(31);
        int price = 1500;
        int minimumOrderAmount = 10000;
        Category category = Category.FOOD;
        LocalDateTime startAt = LocalDateTime.now();
        LocalDateTime endAt = startAt.plusDays(10);

        // When & Then
        assertThatThrownBy(() -> new Coupon(name, price, minimumOrderAmount, category, startAt, endAt))
                .isInstanceOf(CouponException.class);
    }

    @Test
    @DisplayName("최소 주문 금액이 기준보다 작은 경우 예외가 발생한다.")
    void createCouponWithLessThanMinimumOrderAmount() {
        // Given
        String name = "Welcome Discount";
        int price = 1500;
        int minimumOrderAmount = 4900;
        Category category = Category.FOOD;
        LocalDateTime startAt = LocalDateTime.now();
        LocalDateTime endAt = startAt.plusDays(10);

        // When & Then
        assertThatThrownBy(() -> new Coupon(name, price, minimumOrderAmount, category, startAt, endAt))
                .isInstanceOf(CouponException.class);
    }

    @Test
    @DisplayName("최소 주문 금액이 기준보다 큰 경우 예외가 발생한다.")
    void createCouponWithMoreThanMinimumOrderAmount() {
        // Given
        String name = "Welcome Discount";
        int price = 1500;
        int minimumOrderAmount = 100001;
        Category category = Category.FOOD;
        LocalDateTime startAt = LocalDateTime.now();
        LocalDateTime endAt = startAt.plusDays(10);

        // When & Then
        assertThatThrownBy(() -> new Coupon(name, price, minimumOrderAmount, category, startAt, endAt))
                .isInstanceOf(CouponException.class);
    }

    @Test
    @DisplayName("시작일이 종료일보다 늦을 경우 예외가 발생한다.")
    void createCouponWithStartDateAfterEndDate() {
        // Given
        String name = "Welcome Discount";
        int price = 1500;
        int minimumOrderAmount = 10000;
        Category category = Category.FOOD;
        LocalDateTime startAt = LocalDateTime.now().plusDays(10);
        LocalDateTime endAt = LocalDateTime.now();

        // When & Then
        assertThatThrownBy(() -> new Coupon(name, price, minimumOrderAmount, category, startAt, endAt))
                .isInstanceOf(CouponException.class);
    }
}
