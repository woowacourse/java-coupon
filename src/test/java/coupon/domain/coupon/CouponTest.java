package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import coupon.domain.Member;
import coupon.domain.MemberCoupon;

class CouponTest {

    @DisplayName("쿠폰 발급 가능 날짜 범위 안에서는 회원쿠폰을 발급할 수 있다.")
    @Test
    void issueMemberCoupon() {
        // given
        LocalDate couponIssuableDate = LocalDate.now();
        LocalDate memberCouponIssueDate = LocalDate.now();
        Member member = new Member("sancho");
        Coupon coupon = new Coupon("test-coupon", 1000, 10000, couponIssuableDate, couponIssuableDate, Category.ELECTRONICS);

        // when & then
        assertThatCode(() -> coupon.issueMemberCoupon(member, memberCouponIssueDate))
                .doesNotThrowAnyException();
    }

    @DisplayName("회원 쿠폰을 발급하면 사용 가능한 상태로 발급된다.")
    @Test
    void issueMemberCouponUsable() {
        // given
        LocalDate couponIssuableDate = LocalDate.now();
        LocalDate memberCouponIssueDate = LocalDate.now();
        Member member = new Member("sancho");
        Coupon coupon = new Coupon("test-coupon", 1000, 10000, couponIssuableDate, couponIssuableDate, Category.ELECTRONICS);

        // when
        MemberCoupon memberCoupon = coupon.issueMemberCoupon(member, memberCouponIssueDate);

        // then
        assertThat(memberCoupon.isUsed()).isFalse();
    }

    @DisplayName("쿠폰 발급 가능 날짜를 넘어서면 회원 쿠폰을 발급시 예외가 발생한다.")
    @ParameterizedTest
    @MethodSource(value = "datesProvider")
    void issueMemberCouponFail(LocalDate couponIssuableDate, LocalDate memberCouponIssueDate) {
        // given
        Member member = new Member("sancho");
        Coupon coupon = new Coupon("test-coupon", 1000, 10000, couponIssuableDate, couponIssuableDate, Category.ELECTRONICS);

        // when & then
        assertThatThrownBy(() -> coupon.issueMemberCoupon(member, memberCouponIssueDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("쿠폰의 발급 가능 기한에 포함되지 않습니다.");
    }

    static Stream<Arguments> datesProvider() {
        return Stream.of(
                Arguments.of(LocalDate.now(), LocalDate.now().minusDays(1)),
                Arguments.of(LocalDate.now(), LocalDate.now().plusDays(1))
        );
    }
}
