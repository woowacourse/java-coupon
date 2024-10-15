package coupon.application.coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponRepository;
import coupon.exception.CouponException;
import coupon.exception.ExceptionType;
import coupon.support.data.CouponTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @DisplayName("식별자로 쿠폰을 조회한다.")
    @Test
    void getCoupon() {
        Coupon coupon = couponRepository.save(CouponTestData.defaultCoupon().build());

        Coupon actual = couponService.getCoupon(coupon.getId());

        assertThat(actual).isEqualTo(coupon);
    }

    @DisplayName("존재하지 않는 식별자로 조회시 예외가 발생한다.")
    @Test
    void getCouponByNotExistId() {
        Long given = -1L;

        assertThatThrownBy(() -> couponService.getCoupon(given))
                .isInstanceOf(CouponException.class)
                .hasMessage(ExceptionType.COUPON_NOT_FOUND.getMessage());
    }
}
