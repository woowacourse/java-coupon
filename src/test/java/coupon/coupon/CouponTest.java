package coupon.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class CouponTest {

    @Test
    void 쿠폰_생성() {
        // given
        String name = "name";
        int discountAmount = 1000;
        int minOrderAmount = 5000;
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);

        // when, then
        assertDoesNotThrow(() -> new Coupon(name, discountAmount, minOrderAmount, Category.FOOD, startDate, endDate));
    }

    @Test
    void 쿠폰_이름이_없을_경우_예외() {
        // given
        String emptyName = "";
        String nullName = null;

        int discountAmount = 1000;
        int minOrderAmount = 5000;
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);

        // when, then
        assertAll(
                () -> assertThatThrownBy(
                        () -> new Coupon(emptyName, discountAmount, minOrderAmount, Category.FOOD, startDate, endDate))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(
                        () -> new Coupon(nullName, discountAmount, minOrderAmount, Category.FOOD, startDate, endDate))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 쿠폰_이름이_30를_넘을_경우_예외() {
        // given
        String longName = "a".repeat(31);

        int discountAmount = 1000;
        int minOrderAmount = 5000;
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);

        // when, then
        assertThatThrownBy(
                () -> new Coupon(longName, discountAmount, minOrderAmount, Category.FOOD, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 할인_금액이_1000이상_10000이하가_아닐_경우_예외() {
        // given
        int lowDiscountAmount = 999;
        int highDiscountAmount = 10001;

        String name = "name";
        int minOrderAmount = 5000;
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);

        // when, then
        assertAll(
                () -> assertThatThrownBy(
                        () -> new Coupon(name, lowDiscountAmount, minOrderAmount, Category.FOOD, startDate, endDate))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(
                        () -> new Coupon(name, highDiscountAmount, minOrderAmount, Category.FOOD, startDate, endDate))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 할인_금액의_단위가_500이_아닐_경우_예외() {
        // given
        int wrongDiscountAmount = 501;

        String name = "name";
        int minOrderAmount = 5000;
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);

        // when, then
        assertThatThrownBy(
                () -> new Coupon(name, wrongDiscountAmount, minOrderAmount, Category.FOOD, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 최소_주문_금액이_5000이상_100000이하가_아닐_경우_예외() {
        // given
        int lowMinOrderAmount = 4999;
        int highMinOrderAmount = 100001;

        String name = "name";
        int discountAmount = 5000;
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);

        // when, then
        assertAll(
                () -> assertThatThrownBy(
                        () -> new Coupon(name, discountAmount, lowMinOrderAmount, Category.FOOD, startDate, endDate))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(
                        () -> new Coupon(name, discountAmount, highMinOrderAmount, Category.FOOD, startDate, endDate))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 시작일이_종료일보다_이후일_경우_예외() {
        // given
        LocalDateTime startDate = LocalDateTime.now().plusDays(1);
        LocalDateTime endDate = LocalDateTime.now();

        String name = "name";
        int discountAmount = 1000;
        int minOrderAmount = 5000;

        // when, then
        assertThatThrownBy(
                () -> new Coupon(name, discountAmount, minOrderAmount, Category.FOOD, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이미_지난날을_종료일로_설정할_경우_예외() {
        // given
        LocalDateTime startDate = LocalDateTime.now().minusDays(2);
        LocalDateTime endDate = LocalDateTime.now().minusDays(1);

        String name = "name";
        int discountAmount = 1000;
        int minOrderAmount = 5000;

        // when, then
        assertThatThrownBy(
                () -> new Coupon(name, discountAmount, minOrderAmount, Category.FOOD, startDate, endDate))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
