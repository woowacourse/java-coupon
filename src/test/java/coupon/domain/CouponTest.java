package coupon.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class CouponTest {

    // 이름 테스트
    @Test
    void 쿠폰_이름은_반드시_존재해야_한다() {
        assertThatThrownBy(() -> new Coupon(null, 1000, 5000, LocalDateTime.now(), LocalDateTime.now(), Category.FASHION))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("쿠폰 이름은 반드시 존재해야 합니다.");

        assertThatThrownBy(() -> new Coupon("", 1000, 5000, LocalDateTime.now(), LocalDateTime.now(), Category.FASHION))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("쿠폰 이름은 반드시 존재해야 합니다.");
    }

    @Test
    void 쿠폰_이름의_길이는_최대_30자_이하여야_한다() {
        assertThatThrownBy(() -> new Coupon("이름이 매우 매s우 길어서 30자를 초과하는 쿠폰 이름입니다", 1000, 5000, LocalDateTime.now(), LocalDateTime.now(), Category.FASHION))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("쿠폰 이름은 최대 30자 이하여야 합니다.");
    }

    // 할인 금액 테스트
    @Test
    void 할인_금액은_1000원_이상이어야_한다() {
        assertThatThrownBy(() -> new Coupon("쿠폰", 999, 5000, LocalDateTime.now(), LocalDateTime.now(), Category.FASHION))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("할인 금액은 1,000원 이상이어야 합니다.");
    }

    @Test
    void 할인_금액은_10000원_이하여야_한다() {
        assertThatThrownBy(() -> new Coupon("쿠폰", 10001, 5000, LocalDateTime.now(), LocalDateTime.now(), Category.FASHION))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("할인 금액은 10,000원 이하여야 합니다.");
    }

    @Test
    void 할인_금액은_500원_단위로_설정할_수_있다() {
        assertThatThrownBy(() -> new Coupon("쿠폰", 1501, 5000, LocalDateTime.now(), LocalDateTime.now(), Category.FASHION))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("할인 금액은 500원 단위여야 합니다.");
    }

    // 할인율 테스트
    @Test
    void 할인율은_할인금액_나누기_최소_주문금액으로_계산된다() {
        Coupon coupon = new Coupon("쿠폰", 1000, 5000, LocalDateTime.now(), LocalDateTime.now(), Category.FASHION);
        assertThat(coupon.getDiscountRate()).isEqualTo(20);  // 1000 / 5000 = 0.2 -> 20%
    }

    @Test
    void 할인율은_소수점_버림율_처리된다() {
        Coupon coupon = new Coupon("쿠폰", 1000, 30000, LocalDateTime.now(), LocalDateTime.now(), Category.FASHION);
        assertThat(coupon.getDiscountRate()).isEqualTo(3);  // 1500 / 5100 = 0.294 -> 버림 처리하여 29%
    }

    @Test
    void 할인율은_3퍼센트_이상이어야_한다() {
        assertThatThrownBy(() -> new Coupon("쿠폰", 1000, 34000, LocalDateTime.now(), LocalDateTime.now(), Category.FASHION))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("할인율은 3% 이상이어야 합니다.");
    }

    @Test
    void 할인율은_20퍼센트_이하여야_한다() {
        assertThatThrownBy(() -> new Coupon("쿠폰", 3000, 5000, LocalDateTime.now(), LocalDateTime.now(), Category.FASHION))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("할인율은 20% 이하여야 합니다.");
    }

    // 최소 주문 금액 테스트
    @Test
    void 최소_주문_금액은_5000원_이상이어야_한다() {
        assertThatThrownBy(() -> new Coupon("쿠폰", 1000, 4999, LocalDateTime.now(), LocalDateTime.now(), Category.FASHION))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("최소 주문 금액은 5,000원 이상이어야 합니다.");
    }

    @Test
    void 최소_주문_금액은_100000원_이하여야_한다() {
        assertThatThrownBy(() -> new Coupon("쿠폰", 1000, 100001, LocalDateTime.now(), LocalDateTime.now(), Category.FASHION))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("최소 주문 금액은 100,000원 이하여야 합니다.");
    }

    // 카테고리 테스트
    @Test
    void 카테고리는_패션_가전_가구_식품_중_하나여야_한다() {
        assertThatThrownBy(() -> new Coupon("쿠폰", 1000, 5000, LocalDateTime.now(), LocalDateTime.now(), null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("카테고리는 패션, 가전, 가구, 식품 중 하나여야 합니다.");
    }

    // 발급 기간 테스트
    @Test
    void 시작일은_종료일보다_이전이어야_한다() {
        LocalDateTime now = LocalDateTime.now();
        assertThatThrownBy(() -> new Coupon("쿠폰", 1000, 5000, now.plusDays(1), now, Category.FASHION))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("시작일은 종료일보다 이전이어야 합니다.");
    }
}

