package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponCategory;
import coupon.coupon.domain.MemberCoupon;
import coupon.coupon.domain.repository.CouponRepository;
import coupon.coupon.domain.repository.MemberCouponRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private CouponRepository couponRepository;

    @DisplayName("회원에게 쿠폰을 발급한다.")
    @Test
    void issueCoupon() {
        // given
        Long memberId = 1L;
        String name = "냥인의쿠폰";
        BigDecimal discountAmount = BigDecimal.valueOf(1_000);
        BigDecimal minimumOrderPrice = BigDecimal.valueOf(5_000);
        CouponCategory couponCategory = CouponCategory.FOOD;
        LocalDateTime issueStartedAt = LocalDateTime.of(2024, 10, 16, 0, 0, 0, 0);
        LocalDateTime issueEndedAt = LocalDateTime.of(2024, 10, 26, 23, 59, 59, 999_999_000);
        Coupon coupon = new Coupon(
                name, discountAmount, minimumOrderPrice, couponCategory, issueStartedAt, issueEndedAt
        );
        Coupon savedCoupon = couponRepository.save(coupon);

        // when
        MemberCoupon actual = memberCouponService.issueCoupon(memberId, savedCoupon.getId());

        // then
        assertThat(actual.getMemberId()).isEqualTo(memberId);
        assertThat(actual.getCoupon()).isEqualTo(savedCoupon);
    }
}
