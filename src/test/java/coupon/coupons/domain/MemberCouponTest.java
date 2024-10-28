package coupon.coupons.domain;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import coupon.member.domain.Member;
import coupon.membercoupon.domain.MemberCoupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberCouponTest {

    @DisplayName("발급 일시가 없는 경우 예외가 발생한다")
    @Test
    void validateIssuedAt() {
        Coupon coupon = new Coupon("쿠폰", 1000, 5000, Category.FASHION.name(), LocalDateTime.now(), LocalDateTime.now()
                .plusDays(1));
        Member member = new Member("회원이름");

        assertThatThrownBy(() -> new MemberCoupon(coupon, member, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("발급 일시는 반드시 존재해야 합니다.");
    }

    @DisplayName("쿠폰의 만료일은 발급일로부터 7일 후의 23:59:59.999999까지 사용할 수 있다")
    @Test
    void calculateExpiryDate() {
        Coupon coupon = new Coupon("쿠폰", 1000, 5000, Category.FASHION.name(), LocalDateTime.now(), LocalDateTime.now()
                .plusDays(1));
        Member member = new Member("회원이름");

        LocalDateTime issuedAt = LocalDateTime.now();
        MemberCoupon memberCoupon = new MemberCoupon(coupon, member, issuedAt);

        assertThat(memberCoupon.getExpiresAt()).isEqualTo(issuedAt.plusDays(6).withHour(23).withMinute(59)
                .withSecond(59).withNano(999999999));
    }
}
