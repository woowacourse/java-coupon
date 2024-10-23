package coupon;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.service.CouponService;

@SpringBootTest
class CouponServiceTest {

	@Autowired
	private CouponService couponService;

	@Test
	void replicationLagTest() {
		Coupon coupon = new Coupon("가을 맞이 쿠폰", 1000, 10000,
			Category.FASHION, LocalDate.now(), LocalDate.now().plusDays(7));
		couponService.create(coupon);
		Coupon savedCoupon = couponService.getCoupon(coupon.getId());
		assertThat(savedCoupon).isNotNull();
	}
}
