package coupon.service.coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.discount.DiscountType;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void 쿠폰_발급_복제_지연_테스트() {
        // given
        String couponName = "testCoupon";
        int discountPrice = 1000;
        int minOrderPrice = 5000;
        int minDiscountRange = 3;
        int maxDiscountRange = 20;
        Category category = Category.FASHION;
        LocalDate issueStartDate = LocalDate.now().minusDays(3);
        LocalDate issueEndDate = LocalDate.now().plusDays(3);

        // when
        Coupon coupon = couponService.createCoupon(couponName, discountPrice, minOrderPrice, DiscountType.PERCENT,
                minDiscountRange, maxDiscountRange, category, issueStartDate, issueEndDate);
        Coupon savedCoupon = couponService.getById(coupon.getId());

        // then
        assertThat(savedCoupon).isNotNull();
    }
}
