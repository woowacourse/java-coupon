package coupon;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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

	@DisplayName("쿠폰 이름이 30자를 초과하면 예외가 발생한다")
	@Test
	void validateNameLengthTest() {
		String longName = "일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십일";
		LocalDate startAt = LocalDate.of(2024, 1, 1);
		LocalDate endAt = LocalDate.of(2024, 12, 31);

		Assertions.assertThatThrownBy(() ->
				new Coupon(longName, 1000, 10000, Category.FOOD, startAt, endAt))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("이름은 30자 이내로 입력해주세요.");
	}

	@DisplayName("쿠폰의 시작일이 종료일보다 늦으면 예외가 발생한다")
	@Test
	void validateDateTest() {
		String name = "테스트쿠폰";
		LocalDate startAt = LocalDate.of(2024, 12, 31);
		LocalDate endAt = LocalDate.of(2024, 1, 1);

		Assertions.assertThatThrownBy(() ->
				new Coupon(name, 1000, 10000, Category.FOOD, startAt, endAt))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("시작일이 종료일 보다 늦을 수 없습니다.");
	}
}
