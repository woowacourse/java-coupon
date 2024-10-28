package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponEntity;
import coupon.coupon.repository.CouponRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceTest
class CouponLookupServiceTest {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponLookupService couponLookupService;

    @Test
    void 발급_가능한_쿠폰을_조회한다() {
        LocalDate startDate = LocalDate.of(2023, 12, 25);
        LocalDate invalidateStartDate = LocalDate.of(2024, 1, 4);
        LocalDate endDate = LocalDate.of(2024, 1, 5);
        Coupon validCoupon = new Coupon(
                "OK", Category.FOOD, 1_000L, 10_000L, List.of(), startDate, endDate
        );
        Coupon invalidCoupon = new Coupon(
                "NO", Category.FOOD, 1_000L, 10_000L, List.of(), invalidateStartDate, endDate
        );
        couponRepository.save(CouponEntity.from(validCoupon));
        couponRepository.save(CouponEntity.from(invalidCoupon));

        List<CouponResponse> actual = couponLookupService.getAllIssuableCoupons();
        assertAll(
                () -> assertThat(actual).hasSize(1),
                () -> assertThat(actual.get(0)).extracting(CouponResponse::name).isEqualTo("OK")
        );
    }
}
