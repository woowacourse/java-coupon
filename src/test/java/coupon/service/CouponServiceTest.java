package coupon.service;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.repository.CouponRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponRepository couponRepository;

    @DisplayName("쿠폰 생성에 성공한다.")
    @Test
    void create() {
        Coupon coupon = new Coupon("유효한 쿠폰", 1000, 5000, Category.FASHION, LocalDateTime.now(), LocalDateTime.now());
        Coupon saved = couponService.create(coupon);
        assertThat(couponRepository.findById(saved.getId())).isNotNull();
    }

    @DisplayName("쿠폰 조회에 실패하는 경우 예외가 발생한다.")
    @Test
    void failGetCoupon() {
        assertThatThrownBy(() -> couponService.getCoupon(-1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 쿠폰입니다.");
    }

    @Test
    void 복제지연테스트() {
        Coupon coupon = new Coupon("쿠폰", 1000, 10000, Category.FOOD, LocalDateTime.now(), LocalDateTime.now().plusDays(2));
        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
