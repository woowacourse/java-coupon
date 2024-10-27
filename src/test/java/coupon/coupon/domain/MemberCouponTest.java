package coupon.coupon.domain;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import coupon.coupon.CouponException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class MemberCouponTest {

    @DisplayName("쿠폰 발급 일시로 만료 일시를 구한다.")
    @Test
    void calculateExpiredAt() {
        // given
        Coupon coupon = new Coupon("name", 1000, 30000, Category.FASHION, LocalDate.now(), LocalDate.now()
                .plusDays(10));
        Member member = new Member("Prin?");

        // when
        MemberCoupon memberCoupon = MemberCoupon.issue(member, coupon);

        //then
        assertThat(memberCoupon.getIssuedAt().plusDays(7)).isBeforeOrEqualTo(memberCoupon.getExpiredAt());
    }

    @DisplayName("쿠폰 발급 기한 외의 날짜에 발급을 시도하면 예외가 발생한다.")
    @Test
    void cannotIssue() {
        // given
        LocalDate startAt = LocalDate.now().plusDays(1);
        LocalDate endAt = LocalDate.now().plusDays(10);
        Coupon coupon = new Coupon("name", 1000, 30000, Category.FASHION, startAt, endAt);
        Member member = new Member("Prin~");

        // when
        assertThatThrownBy(() -> MemberCoupon.issue(member, coupon))
                .isInstanceOf(CouponException.class)
                .hasMessage("쿠폰을 발급할 수 있는 기한이 지났습니다.");
    }
}
