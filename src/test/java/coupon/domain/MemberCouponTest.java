package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;

class MemberCouponTest {

    @DisplayName("회원 쿠폰을 사용한다.")
    @ParameterizedTest
    @MethodSource(value = "validDatesProvider")
    void use(LocalDate useDate) {
        // given
        LocalDate couponIssuableDate = LocalDate.now();
        Coupon coupon = new Coupon("test-coupon", 1000, 10000, couponIssuableDate, couponIssuableDate, Category.ELECTRONICS);

        Member member = new Member("sancho");
        LocalDate memberCouponIssuedAt = LocalDate.now();
        MemberCoupon memberCoupon = new MemberCoupon(member, coupon, memberCouponIssuedAt);

        // when
        memberCoupon.use(useDate);

        // then
        assertThat(memberCoupon.isUsed()).isTrue();
    }

    static Stream<Arguments> validDatesProvider() {
        return Stream.of(
                Arguments.of(LocalDate.now()),
                Arguments.of(LocalDate.now().plusDays(6))
        );
    }

    @DisplayName("이미 사용된 회원 쿠폰을 사용하려하면 예외가 발생한다.")
    @Test
    void useFailByAlreadyUsed() {
        // given
        LocalDate couponIssuableDate = LocalDate.now();
        Coupon coupon = new Coupon("test-coupon", 1000, 10000, couponIssuableDate, couponIssuableDate, Category.ELECTRONICS);

        Member member = new Member("sancho");
        LocalDate memberCouponIssuedAt = LocalDate.now();
        MemberCoupon memberCoupon = new MemberCoupon(member, coupon, memberCouponIssuedAt);
        LocalDate useDate = LocalDate.now();
        memberCoupon.use(useDate);

        // when & then
        assertThatThrownBy(() -> memberCoupon.use(useDate))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 사용된 쿠폰은 사용할 수 없습니다.");
    }

    @DisplayName("사용 가능 기간을 벗어난 회원 쿠폰을 사용하려하면 예외가 발생한다.")
    @ParameterizedTest
    @MethodSource(value = "invalidDatesProvider")
    void useFailByPeriod(LocalDate useDate) {
        // given
        LocalDate couponIssuableDate = LocalDate.now();
        Coupon coupon = new Coupon("test-coupon", 1000, 10000, couponIssuableDate, couponIssuableDate, Category.ELECTRONICS);

        Member member = new Member("sancho");
        LocalDate memberCouponIssuedAt = LocalDate.now();
        MemberCoupon memberCoupon = new MemberCoupon(member, coupon, memberCouponIssuedAt);

        // when & then
        assertThatThrownBy(() -> memberCoupon.use(useDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("쿠폰이 사용한 기간을 벗어났습니다.");
    }

    static Stream<Arguments> invalidDatesProvider() {
        return Stream.of(
                Arguments.of(LocalDate.now().plusDays(7)),
                Arguments.of(LocalDate.now().minusDays(1))
        );
    }
}
