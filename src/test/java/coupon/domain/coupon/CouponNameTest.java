package coupon.domain.coupon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import coupon.exception.CouponException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponNameTest {

    @DisplayName("쿠폰 이름을 생성한다.")
    @Test
    void create() {
        CouponName couponName = new CouponName("쿠폰 이름");
        assertEquals("쿠폰 이름", couponName.getName());
    }

    @DisplayName("쿠폰 이름은 null이면 예외가 발생한다.")
    @Test
    void createNullName() {
        assertThrows(IllegalArgumentException.class, () -> new CouponName(null));
    }

    @DisplayName("쿠폰 이름이 1자 미만이라면 예외가 발생한다.")
    @Test
    void createBlankName() {
        assertThrows(CouponException.class, () -> new CouponName(""));
    }

    @DisplayName("쿠폰 이름의 길이는 최대 30자 이하여야 한다.")
    @Test
    void nameMaxLength() {
        assertThrows(CouponException.class, () -> new CouponName("1234567890123456789012345678901"));
    }
}
