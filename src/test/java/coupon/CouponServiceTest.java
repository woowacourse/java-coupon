package coupon;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.service.CouponService;

@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService sut;

    @Test
    void 복제지연테스트() {
        // given
        var coupon = new Coupon(
                "쿠폰 이름",
                1000L,
                10000L,
                Category.FASHION,
                LocalDate.now(),
                LocalDate.now().plusDays(1L));
        coupon = sut.create(coupon);

        // when
        var savedCoupon = sut.getCoupon(coupon.getId());

        // then
        assertThat(savedCoupon).isNotNull();
    }
}
