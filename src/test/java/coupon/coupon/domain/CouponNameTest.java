package coupon.coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import coupon.coupon.CouponException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CouponNameTest {

    @DisplayName("이름이 없으면 예외가 발생한다.")
    @Test
    void cannotCreateIfNoName() {
        assertThatThrownBy(() -> new CouponName(null))
                .isInstanceOf(CouponException.class)
                .hasMessage("쿠폰 이름이 누락되었습니다.");
    }

    @DisplayName("이름이 30자를 초과하면 예외가 발생한다.")
    @Test
    void cannotCreateIfExceedLength() {
        // given
        String name = "가".repeat(31);

        // when & then
        assertThatThrownBy(() -> new CouponName(name))
                .isInstanceOf(CouponException.class)
                .hasMessage("쿠폰은 30자 이하의 이름을 설정해주세요.");
    }
}
