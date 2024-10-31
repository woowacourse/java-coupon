package coupon.service.coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.CouponFixture;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.MemberCoupon;
import coupon.exception.CouponException;
import coupon.service.coupon.dto.MemberCouponsResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void replicationLagTest() {
        Coupon coupon = CouponFixture.FOOD_COUPON.create();

        couponService.create(coupon);

        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }

    @DisplayName("한 회원에게 동일한 쿠폰을 5개 발급한다.")
    @Test
    void issue() {
        Coupon coupon = CouponFixture.FOOD_COUPON.create();
        couponService.create(coupon);

        for (int i = 0; i < 5; i++) {
            MemberCoupon memberCoupon = couponService.issue(1L, coupon.getId());
            assertThat(memberCoupon).isNotNull();
        }
    }

    @DisplayName("한 회원에게 동일한 쿠폰을 6개 이상 발급하면 예외가 발생한다.")
    @Test
    void issueFailedSixIssue() {
        Coupon coupon = CouponFixture.FOOD_COUPON.create();
        couponService.create(coupon);
        for (int i = 0; i < 5; i++) {
            couponService.issue(1L, coupon.getId());
        }

        assertThatThrownBy(() -> couponService.issue(1L, coupon.getId()))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("한 회원에게 동일한 쿠폰을 6개 이상 발급하면 예외가 발생한다.")
    @Test
    void issueFailedPastWeek() {
        Coupon coupon = CouponFixture.PAST_WEEK_COUPON.create();
        couponService.create(coupon);

        assertThatThrownBy(() -> couponService.issue(1L, coupon.getId()))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("쿠폰 및 회원에게 발급된 쿠폰의 정보를 조회한다.")
    @Test
    void getMemberCoupons() {
        Coupon foodCoupon = CouponFixture.FOOD_COUPON.create();
        Coupon fashionCoupon = CouponFixture.FASHION_COUPON.create();
        couponService.create(foodCoupon);
        couponService.create(fashionCoupon);
        Long memberId = 1L;
        for (int i = 0; i < 5; i++) {
            couponService.issue(memberId, foodCoupon.getId());
            couponService.issue(memberId, fashionCoupon.getId());
        }

        List<MemberCouponsResponse> memberCoupons = couponService.getMemberCoupons(memberId);
        assertThat(memberCoupons).hasSize(10);
    }
}
