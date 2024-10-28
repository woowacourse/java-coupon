package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import coupon.coupon.domain.Category;
import coupon.coupon.repository.CouponEntity;
import coupon.coupon.repository.CouponRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(OutputCaptureExtension.class)
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    void 쿠폰을_생성한_뒤_곧바로_조회하면_쓰기_DB에서_불러온다(CapturedOutput output) {
        // given
        CouponCreationRequest request = new CouponCreationRequest(
                "쿠폰", Category.FOOD,
                1000, 10000,
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31));

        // when
        CouponEntity created = couponService.create(request);

        // then
        assertThatCode(() -> couponService.getCoupon(created.getId()))
                .doesNotThrowAnyException();
        assertThat(output).doesNotContain("read");
    }

    @Test
    void 쿠폰을_생성한_뒤_일정_시간_뒤_조회하면_읽기_DB에서_불러온다(CapturedOutput output) throws InterruptedException {
        // given
        CouponCreationRequest request = new CouponCreationRequest(
                "쿠폰", Category.FOOD,
                1000, 10000,
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31));

        // when
        CouponEntity created = couponService.create(request);
        Thread.sleep(3000);

        // then
        assertThatCode(() -> couponService.getCoupon(created.getId()))
                .doesNotThrowAnyException();
        assertThat(output).contains("read");
    }
}
