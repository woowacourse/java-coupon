package coupon.application.membercoupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import coupon.application.coupon.CouponService;
import coupon.domain.Coupon;
import coupon.domain.CouponCategory;
import coupon.domain.CouponIssuableDuration;
import coupon.domain.CouponName;
import coupon.support.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MemberCouponServiceTest extends IntegrationTestSupport {

    @Autowired
    private CouponService couponService;

    @Autowired
    private MemberCouponService memberCouponService;

    @Test
    @DisplayName("회원의 쿠폰 목록을 조회한다.")
    void getMemberCoupons() {
        Coupon coupon1 = createCoupon();
        couponService.create(coupon1);
        Coupon coupon2 = createCoupon();
        couponService.create(coupon2);
        issueCoupons(1L, coupon1, 3);
        issueCoupons(1L, coupon2, 3);

        List<MemberCouponResponse> responses = memberCouponService.getMemberCoupons(1L);

        Set<IssuedCouponResponse> coupons = responses.stream()
                .map(MemberCouponResponse::coupon)
                .collect(Collectors.toSet());
        assertAll(
                () -> assertThat(responses).hasSize(6),
                () -> assertThat(coupons)
                        .hasSize(2)
                        .map(IssuedCouponResponse::id)
                        .containsExactlyInAnyOrder(coupon1.getId(), coupon2.getId())
        );
    }

    private void issueCoupons(Long memberId, Coupon coupon, int times) {
        for (int i = 0; i < times; i++) {
            couponService.issue(memberId, coupon.getId());
        }
    }

    private Coupon createCoupon() {
        LocalDate today = LocalDate.now();
        LocalDate end = today.plusDays(1);

        return new Coupon(
                new CouponName("1,000원 할인"),
                CouponCategory.FOOD,
                new CouponIssuableDuration(today, end),
                "1000",
                "10000"
        );
    }
}
