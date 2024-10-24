package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.coupon.Coupon;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @DisplayName("저장 후 바로 읽을 수 있다.")
    @Test
    void createAndGet() {
        Coupon coupon = new Coupon("쿠폰", 10000, 1000, "FOOD", LocalDate.now(), LocalDate.now());
        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }

    @DisplayName("존재하지 않는 아이디의 쿠폰을 가져오면 예외가 발생한다.")
    @Test
    void getCouponWhenNotExist() {
        assertThatThrownBy(() -> couponService.getCoupon(1000L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 아이디의 쿠폰이 존재하지 않습니다.");
    }
}
