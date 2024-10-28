package coupon.service.coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.entity.coupon.Coupon;
import coupon.entity.coupon.CouponCategory;
import coupon.exception.coupon.CouponNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void 쿠폰을_생성할_수_있다() {
        // given
        LocalDateTime stamp = LocalDateTime.now();
        String rightName = "생성" + stamp;
        Coupon coupon = new Coupon(
                rightName,
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(10000),
                CouponCategory.FOOD,
                LocalDate.of(2000, 4, 7),
                LocalDate.of(2000, 4, 12)
        );

        // when
        Long id = couponService.create(coupon);

        // then
        assertThat(couponService.getCouponFromWriterDatabase(id).getName().getName()).isEqualTo(rightName);
    }

    @Test
    void 생성_후_시간이_흐르고서_쿠폰을_조회하면_복제지연이_발생하지_않는다() throws InterruptedException {
        // given
        LocalDateTime stamp = LocalDateTime.now();
        String rightName = "조회" + stamp;
        Coupon coupon = new Coupon(
                rightName,
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(10000),
                CouponCategory.FOOD,
                LocalDate.of(2000, 4, 7),
                LocalDate.of(2000, 4, 12)
        );
        couponService.create(coupon);

        // when
        Thread.sleep(2000); // 복제 지연 대기
        Coupon findCoupon = couponService.getCouponWithReplicaLag(coupon.getId());

        // then
        assertThat(findCoupon.getName().getName()).isEqualTo(rightName);
    }

    @Test
    void 생성_후_즉시_쿠폰을_조회하면_복제지연이_발생한다() {
        // given
        LocalDateTime stamp = LocalDateTime.now();
        String rightName = "지연" + stamp;
        Coupon coupon = new Coupon(
                rightName,
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(10000),
                CouponCategory.FOOD,
                LocalDate.of(2000, 4, 7),
                LocalDate.of(2000, 4, 12)
        );
        couponService.create(coupon);

        // when && then
        assertThatThrownBy(() -> couponService.getCouponWithReplicaLag(coupon.getId()))
                .isInstanceOf(CouponNotFoundException.class);
    }

    @Test
    void 생성_후_즉시_쿠폰을_조회할때_복제지연_영향_안받을_수_있다() {
        // given
        LocalDateTime stamp = LocalDateTime.now();
        String rightName = "정상" + stamp;
        Coupon coupon = new Coupon(
                rightName,
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(10000),
                CouponCategory.FOOD,
                LocalDate.of(2000, 4, 7),
                LocalDate.of(2000, 4, 12)
        );
        couponService.create(coupon);

        // when
        Coupon findCoupon = couponService.getCouponWithoutReplicaLag(coupon.getId());

        // then
        assertThat(findCoupon.getName().getName()).isEqualTo(rightName);
    }
}
