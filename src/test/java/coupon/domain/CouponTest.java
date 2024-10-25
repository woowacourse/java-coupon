package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponTest {

    @Test
    @DisplayName("사용자 쿠폰을 발급할 수 있다.")
    void issue() {
        Coupon coupon = createCoupon(LocalDate.now(), LocalDate.now());
        Long memberId = 1L;

        MemberCoupon memberCoupon = coupon.issue(memberId);

        assertAll(
                () -> assertThat(memberCoupon.getCouponId()).isEqualTo(coupon.getId()),
                () -> assertThat(memberCoupon.getMemberId()).isEqualTo(memberId),
                () -> assertThat(memberCoupon.getIsUsed()).isFalse()
        );
    }

    @Test
    @DisplayName("발급 기간이 지난 쿠폰은 발급할 수 없다.")
    void invalidDate() {
        LocalDate today = LocalDate.now();
        LocalDate start = today.minusDays(2);
        LocalDate end = today.minusDays(1);
        Coupon coupon = createCoupon(start, end);
        Long memberId = 1L;

        assertThatThrownBy(() -> coupon.issue(memberId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("쿠폰 발급 기간이 지났습니다.");
    }

    private Coupon createCoupon(LocalDate start, LocalDate end) {
        return new Coupon(
                new CouponName("1,000원 할인"),
                CouponCategory.FOOD,
                new CouponIssuableDuration(start, end),
                "1000",
                "10000"
        );
    }
}
