package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.domain.Category;
import coupon.domain.Coupon;

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
        var saved = sut.create(coupon);

        // when
        var actual = sut.getCoupon(saved.getId());

        // then
        assertThat(actual).isNotNull();
    }
}
