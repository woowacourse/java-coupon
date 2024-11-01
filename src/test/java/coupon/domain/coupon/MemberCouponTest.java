package coupon.domain.coupon;

import static org.junit.jupiter.api.Assertions.assertThrows;

import coupon.exception.CouponException;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    private final LocalDate today = LocalDate.now();
    private final Coupon coupon = new Coupon(
            "쿠폰 이름",
            10000,
            1000,
            Category.FOOD,
            today.minusDays(10),
            today.plusDays(1)
    );

    @DisplayName("쿠폰의 발급 종료일 이후의 회원 쿠폰을 생성하면 예외가 발생한다.")
    @Test
    void createAfterIssuanceEnd() {
        assertThrows(
                CouponException.class,
                () -> new MemberCoupon(1L, coupon, today.plusDays(2).atStartOfDay())
        );
    }
}
