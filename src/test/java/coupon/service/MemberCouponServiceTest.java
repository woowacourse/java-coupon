package coupon.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.coupon.Coupon;
import coupon.repository.CouponRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;
    @Autowired
    private CouponRepository couponRepository;

    @Test
    @DisplayName("쿠폰 발급 가능 기간이 아닐 때 멤버 쿠폰을 발급하려 하면 예외가 발생한다.")
    void validateCouponCanIssue() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        Coupon coupon = new Coupon("쿠폰", 10000, 1000, "FOOD", yesterday, yesterday);
        Coupon savedCoupon = couponRepository.save(coupon);

        assertThatThrownBy(() -> memberCouponService.issueCoupon(savedCoupon.getId(), 1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("쿠폰 발급 가능 기간이 아닙니다.");
    }

    @Test
    @DisplayName("한 명의 회원이 동일한 쿠폰을 5장 초과로 발급받으려 하면 예외가 발생한다.")
    void validateMemberCanGet() {
        LocalDate today = LocalDate.now();
        Coupon coupon = new Coupon("쿠폰", 10000, 1000, "FOOD", today, today);
        Coupon savedCoupon = couponRepository.save(coupon);
        Long couponId = savedCoupon.getId();

        memberCouponService.issueCoupon(couponId, 1L);
        memberCouponService.issueCoupon(couponId, 1L);
        memberCouponService.issueCoupon(couponId, 1L);
        memberCouponService.issueCoupon(couponId, 1L);
        memberCouponService.issueCoupon(couponId, 1L);

        assertThatThrownBy(() -> memberCouponService.issueCoupon(couponId, 1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("동일한 쿠폰은 5장까지만 발급받을 수 있습니다.");
    }
}
