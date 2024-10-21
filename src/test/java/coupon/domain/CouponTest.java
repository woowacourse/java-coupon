package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CouponTest {

    @DisplayName("쿠폰을 생성한다.")
    @Test
    void create() {
        String name = "할인 쿠폰";
        int discount = 1_000;
        int minAmount = 10_000;
        LocalDate startDate = LocalDate.of(2024, 10, 1);
        LocalDate endDate = LocalDate.of(2024, 10, 31);
        Category category = Category.FOOD;

        Coupon coupon = new Coupon(name, discount, minAmount, startDate, endDate, category);

        assertThat(coupon.getName()).isEqualTo(name);
        assertThat(coupon.getDiscount()).isEqualTo(discount);
        assertThat(coupon.getMinAmount()).isEqualTo(minAmount);
        assertThat(coupon.getStartDate()).isEqualTo(startDate);
        assertThat(coupon.getEndDate()).isEqualTo(endDate);
        assertThat(coupon.getCategory()).isEqualTo(category);
    }

    @DisplayName("쿠폰 이름이 유효하지 않으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "1234567890123456789012345678901"})
    void createWithInvalidName(String name) {
        int discount = 1_000;
        int minAmount = 10_000;
        LocalDate startDate = LocalDate.of(2024, 10, 1);
        LocalDate endDate = LocalDate.of(2024, 10, 31);
        Category category = Category.FOOD;

        assertThatThrownBy(() -> new Coupon(name, discount, minAmount, startDate, endDate, category))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 쿠폰 이름입니다.");
    }

    @DisplayName("할인 금액이 유효하지 않으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {999, 10_001})
    void createWithInvalidDiscount(int discount) {
        String name = "할인 쿠폰";
        int minAmount = 10_000;
        LocalDate startDate = LocalDate.of(2024, 10, 1);
        LocalDate endDate = LocalDate.of(2024, 10, 31);
        Category category = Category.FOOD;

        assertThatThrownBy(() -> new Coupon(name, discount, minAmount, startDate, endDate, category))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 할인 금액입니다");
    }

    @DisplayName("쿠폰 할인 금액이 500원 단위가 아니면 예외가 발생한다.")
    @Test
    void createWithInvalidDiscountUnit() {
        String name = "할인 쿠폰";
        int discount = 1_200;
        int minAmount = 10_000;
        LocalDate startDate = LocalDate.of(2024, 10, 1);
        LocalDate endDate = LocalDate.of(2024, 10, 31);
        Category category = Category.FOOD;

        assertThatThrownBy(() -> new Coupon(name, discount, minAmount, startDate, endDate, category))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰 할인 금액은 500원 단위로 설정되어야 합니다.");
    }

    @DisplayName("최소 주문 금액이 유효하지 않으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {4_999, 100_001})
    void createWithInvalidMinAmount(int minAmount) {
        String name = "할인 쿠폰";
        int discount = 1_000;
        LocalDate startDate = LocalDate.of(2024, 10, 1);
        LocalDate endDate = LocalDate.of(2024, 10, 31);
        Category category = Category.FOOD;

        assertThatThrownBy(() -> new Coupon(name, discount, minAmount, startDate, endDate, category))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 최소 주문 금액입니다.");
    }

    @DisplayName("할인율이 유효하지 않으면 예외 발생")
    @Test
    void createWithInvalidDiscountRate() {
        String name = "할인 쿠폰";
        int discount = 1_000;
        int minAmount = 100_000;
        LocalDate startDate = LocalDate.of(2024, 10, 1);
        LocalDate endDate = LocalDate.of(2024, 10, 31);
        Category category = Category.FOOD;

        assertThatThrownBy(() -> new Coupon(name, discount, minAmount, startDate, endDate, category))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 할인율입니다.");
    }

    @DisplayName("시작일이 종료일보다 이후일 때 예외 발생")
    @Test
    void createWithInvalidPeriod() {
        String name = "할인 쿠폰";
        int discount = 1000;
        int minAmount = 10000;
        LocalDate startDate = LocalDate.of(2024, 11, 1); // 종료일보다 이후
        LocalDate endDate = LocalDate.of(2024, 10, 31);
        Category category = Category.FOOD;

        assertThatThrownBy(() -> new Coupon(name, discount, minAmount, startDate, endDate, category))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작일은 종료일보다 이전이어야 합니다.");
    }
}
