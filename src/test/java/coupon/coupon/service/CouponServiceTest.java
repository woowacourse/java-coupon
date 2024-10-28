package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import coupon.coupon.domain.CouponCategory;
import coupon.coupon.domain.CouponEntity;
import coupon.coupon.repository.CouponRepository;
import coupon.coupon.support.TransactionExecutor;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private TransactionExecutor transactionExecutor;

    @Test
    void 쿠폰을_생성한다() {
        // given
        String couponName = "couponName";
        LocalDateTime couponStartAt = LocalDateTime.of(1, 1, 1, 1, 1);

        // when
        long couponId = couponService.createCoupon(couponName, 5000, 50000, CouponCategory.FOOD,
                couponStartAt, couponStartAt.plusDays(1));

        // then
        CouponEntity actual = transactionExecutor.executeByWriter(() -> couponService.getCoupon(couponId));
        assertThat(actual.getCouponName()).isEqualTo(couponName);
    }

    @Test
    void 쿠폰을_조회한다() {
        // given
        String couponName = "couponName";
        LocalDateTime couponStartAt = LocalDateTime.of(1, 1, 1, 1, 1);
        CouponEntity coupon = couponRepository.save(
                new CouponEntity("couponName", 5000, 50000, CouponCategory.FOOD,
                        couponStartAt, couponStartAt.plusDays(1)));

        // when
        CouponEntity actual = transactionExecutor.executeByWriter(() -> couponService.getCoupon(coupon.getId()));

        // then
        assertAll(
                () -> assertThat(actual.getId()).isEqualTo(coupon.getId()),
                () -> assertThat(actual.getCouponName()).isEqualTo(couponName)
        );
    }
}
