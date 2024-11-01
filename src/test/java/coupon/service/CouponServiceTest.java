package coupon.service;

import static org.assertj.core.api.Assertions.assertThatCode;

import coupon.domain.Coupon;
import coupon.dto.CouponRequest;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponServiceTest {

    CouponService couponService;
    MemberService memberService;

    @Autowired
    public CouponServiceTest(CouponService couponService,
                             MemberService memberService) {
        this.couponService = couponService;
        this.memberService = memberService;
    }

    @Test
    @DisplayName("복제 지연 테스트")
    void replicationLagTest() {
        CouponRequest couponRequest = new CouponRequest(
                "쿠폰1",
                10000L,
                2000L,
                1L,
                LocalDate.now(),
                LocalDate.now().plusDays(7));

        Coupon coupon = couponService.create(couponRequest);
        assertThatCode(() -> couponService.getCoupon(coupon.getId()))
                .doesNotThrowAnyException();
    }
}
