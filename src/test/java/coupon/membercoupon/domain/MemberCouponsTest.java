package coupon.membercoupon.domain;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import coupon.coupons.domain.Category;
import coupon.coupons.domain.Coupon;
import coupon.coupons.domain.CouponName;
import coupon.coupons.domain.Coupons;
import coupon.coupons.domain.Duration;
import coupon.coupons.domain.PricingCondition;
import coupon.exception.CouponException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberCouponsTest {

    @DisplayName("회원 쿠폰이 최대 발급 수를 초과하는 경우 예외가 발생한다.")
    @Test
    void validateCouponIssuanceLimit() {
        MemberCoupons memberCoupons = new MemberCoupons(
                List.of(new MemberCoupon(1L, 1L, LocalDateTime.now()),
                        new MemberCoupon(2L, 1L, LocalDateTime.now().plusHours(1)),
                        new MemberCoupon(3L, 1L, LocalDateTime.now().plusHours(2)),
                        new MemberCoupon(4L, 1L, LocalDateTime.now().plusHours(3)),
                        new MemberCoupon(5L, 1L, LocalDateTime.now().plusHours(4))));

        assertThatThrownBy(() -> memberCoupons.validateCouponIssuanceLimit(1))
                .isInstanceOf(CouponException.class)
                .hasMessage("최대로 발급 받을 수 있는 쿠폰 수는 5개 입니다.");
    }

    @DisplayName("회원 쿠폰 목록에서 쿠폰 ID 리스트를 정상적으로 반환한다.")
    @Test
    void getCouponsIds() {
        MemberCoupons memberCoupons = new MemberCoupons(
                List.of(new MemberCoupon(1L, 1L, LocalDateTime.now()),
                        new MemberCoupon(2L, 1L, LocalDateTime.now().plusHours(1)),
                        new MemberCoupon(3L, 1L, LocalDateTime.now().plusHours(2)),
                        new MemberCoupon(4L, 1L, LocalDateTime.now().plusHours(3))));

        assertThat(memberCoupons.getCouponsIds()).isEqualTo(List.of(1L, 2L, 3L, 4L));
    }

    @DisplayName("쿠폰 목록과 연계하여 MemberCouponDetail 리스트를 반환할 수 있다.")
    @Test
    void getMemberCouponDetails() {
        MemberCoupon memberCoupon1 = new MemberCoupon(1L, 1L, LocalDateTime.now());
        MemberCoupon memberCoupon2 = new MemberCoupon(2L, 1L, LocalDateTime.now().plusHours(1));
        MemberCoupons memberCoupons = new MemberCoupons(List.of(memberCoupon1, memberCoupon2));

        Coupon coupon1 = new Coupon(1L, new CouponName("유효한 쿠폰"), new PricingCondition(1000, 5000), Category.FASHION, new Duration(LocalDateTime.now(), LocalDateTime.now()));
        Coupon coupon2 = new Coupon(2L, new CouponName("유효한 쿠폰"), new PricingCondition(1000, 5000), Category.FASHION, new Duration(LocalDateTime.now(), LocalDateTime.now()));
        Coupons coupons = new Coupons(List.of(coupon1, coupon2));

        assertThat(memberCoupons.getMemberCouponDetails(coupons)).isEqualTo(List.of(new MemberCouponDetail(memberCoupon1, coupon1), new MemberCouponDetail(memberCoupon2, coupon2)));
    }
}
