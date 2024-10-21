package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.vo.DiscountAmount;
import coupon.domain.vo.IssuePeriod;
import coupon.domain.vo.MinimumOrderPrice;
import coupon.domain.vo.Name;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    @DisplayName("복제 지연으로 쿠폰이 조회되지 않는 현상을 방지한다.")
    void occurReplicationLag() {
        Name name = new Name("쿠폰이름");
        DiscountAmount discountAmount = new DiscountAmount(1_000);
        MinimumOrderPrice minimumOrderPrice = new MinimumOrderPrice(30_000);
        IssuePeriod issuePeriod = new IssuePeriod(LocalDateTime.now(), LocalDateTime.now());

        Coupon coupon = new Coupon(name, discountAmount, minimumOrderPrice, Category.FASHION, issuePeriod);

        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCouponInReplicationLag(coupon.getId());

        assertThat(savedCoupon).isNotNull();
    }
}
