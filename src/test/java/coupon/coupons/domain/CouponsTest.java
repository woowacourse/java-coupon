package coupon.coupons.domain;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import coupon.exception.CouponException;
import coupon.member.domain.Member;
import coupon.membercoupon.domain.MemberCoupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CouponsTest {

    @DisplayName("특정 MemberCoupon에 해당하는 Coupon이 없는 경우 예외가 발생한다.")
    @Test
    void findCouponBy() {
        Coupon coupon1 = new Coupon(1L, new CouponName("유효한 쿠폰"), new PricingCondition(1000, 5000), Category.FASHION, new Duration(LocalDateTime.now(), LocalDateTime.now()));
        Coupon coupon2 = new Coupon(2L, new CouponName("유효한 쿠폰"), new PricingCondition(1000, 5000), Category.FASHION, new Duration(LocalDateTime.now(), LocalDateTime.now()));
        Coupons coupons = new Coupons(List.of(coupon1, coupon2));

        Coupon coupon3 = new Coupon(3L, new CouponName("유효하지 않은 쿠폰"), new PricingCondition(1000, 5000), Category.FASHION, new Duration(LocalDateTime.now(), LocalDateTime.now()));
        Member member = new Member("회원이름");
        MemberCoupon memberCoupon = new MemberCoupon(coupon3, member, LocalDateTime.now());

        assertThatThrownBy(() -> coupons.findCouponBy(memberCoupon))
                .isInstanceOf(CouponException.class)
                .hasMessage("해당 MemberCoupon에 해당하는 Coupon이 존재하지 않습니다.");
    }
}
