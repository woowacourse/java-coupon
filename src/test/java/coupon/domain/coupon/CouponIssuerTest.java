package coupon.domain.coupon;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import coupon.domain.member.Member;
import coupon.support.Dummy;

class CouponIssuerTest {

    @Test
    @DisplayName("사용자와 발급할 쿠폰정보를 입력받아 쿠폰을 발급한다.")
    void issue_coupon() {
        // given
        final Coupon coupon = Dummy.createCoupon("coupon");
        final Member member = new Member(1L, "fram");
        final CouponIssuer sut = new CouponIssuer(coupon, member);

        // when
        final Coupon actual = sut.issue(List.of());

        // then
        assertThat(actual.getCouponName()).isEqualTo(coupon.getCouponName());
    }

    @Test
    @DisplayName("단일 사용자가 5개를 초과한 동일 쿠폰 소유 요청 시 예외를 던진다.")
    void validate_coupon() {
        // given
        final Coupon coupon = Dummy.createCoupon("coupon");
        final Member member = new Member(1L, "fram");
        final CouponIssuer sut = new CouponIssuer(coupon, member);

        // when & then
        assertThatThrownBy(() -> sut.issue(List.of(coupon, coupon, coupon, coupon, coupon)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
