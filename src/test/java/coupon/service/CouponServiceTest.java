package coupon.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.dto.CouponRequest;
import coupon.dto.MemberRequest;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponServiceTest {

    CouponService couponService;
    MemberService memberService;

    @Autowired
    public CouponServiceTest(CouponService couponService, MemberService memberService) {
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

    @ParameterizedTest
    @MethodSource("provideCouponRequestWithNotIssueDate")
    @DisplayName("쿠폰을 발급할 때 발급 기한이 아닌 경우 예외를 발생시킨다.")
    void issueCouponWithNotIssueDate(CouponRequest couponRequest) {
        // given
        Coupon coupon = couponService.create(couponRequest);
        Member member = memberService.create(new MemberRequest("낙낙"));

        // when & then
        assertThatThrownBy(() -> couponService.issueCoupon(coupon, member))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰을 발급할 수 있는 기간이 아닙니다.");
    }

    private static List<CouponRequest> provideCouponRequestWithNotIssueDate() {
        return List.of(
                new CouponRequest(
                        "쿠폰1",
                        10000L,
                        2000L,
                        1L,
                        LocalDate.now().minusDays(1),
                        LocalDate.now().minusDays(1)
                ),
                new CouponRequest(
                        "쿠폰1",
                        10000L,
                        2000L,
                        1L,
                        LocalDate.now().plusDays(1),
                        LocalDate.now().plusDays(1)
                )
        );
    }
}
