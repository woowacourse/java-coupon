package coupon.membercoupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import coupon.coupon.domain.Coupon;
import coupon.support.data.CouponTestData;
import coupon.support.data.MemberCouponTestData;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @Test
    @DisplayName("회원 쿠폰이 정상 생성된다.")
    void createMemberCoupon() {
        // when & then
        assertThatCode(() -> MemberCouponTestData.defaultMemberCoupon().build())
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("회원 쿠폰이 정상 발급된다.")
    void issue() {
        // given
        Coupon coupon = CouponTestData.defaultCoupon().build();

        // when
        MemberCoupon memberCoupon = MemberCoupon.issue(1L, coupon);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(memberCoupon.isUsed()).isFalse();
            softly.assertThat(memberCoupon.getExpiredAt()).isEqualTo(coupon.getIssueEndedAt().plusDays(7));
        });
    }
}
