package coupon.api.service;

import coupon.api.repository.CouponRepository;
import coupon.common.exception.CouponNotFoundException;
import coupon.domain.coupon.Category;
import coupon.domain.coupon.CouponDomain;
import coupon.entity.Coupon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponRepository couponRepository;

    @Test
    @DisplayName("Replication이 딜레이 되더라도 쿠폰 조회에 영향이 가지 않는다.")
    void replicationDelay() {
        int couponSize = couponRepository.findAll().size();

        CouponDomain coupon = new CouponDomain("coupon",
                1000,
                10000,
                Category.FASHION,
                LocalDate.now(),
                LocalDate.now().plusDays(3)
        );
        couponService.create(coupon);

        Coupon savedCoupon = couponService.searchCoupon((long) (couponSize + 1));
        assertThat(savedCoupon).isNotNull();
    }

    @Test
    @DisplayName("존재하지 않는 쿠폰인 경우 에러를 발생한다.")
    void searchCoupon_whenNotExist() {
        assertThatThrownBy(() -> couponService.searchCoupon(Long.MAX_VALUE))
                .isInstanceOf(CouponNotFoundException.class);
    }
}
