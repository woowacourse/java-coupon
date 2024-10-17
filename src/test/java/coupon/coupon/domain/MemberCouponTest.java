package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.member.domain.Member;
import coupon.util.CouponFixture;
import coupon.util.MemberFixture;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("MemberCoupon 도메인 테스트")
class MemberCouponTest {

    @DisplayName("회원에게 발급된 쿠폰은 발급일 포함 7일 동안 사용 가능하다.")
    @Test
    void issueEndDate() {
        // given
        Coupon coupon = CouponFixture.createValidFoodCoupon();
        Member member = MemberFixture.createChocochip();
        LocalDateTime now = LocalDateTime.now();

        // when
        MemberCoupon memberCoupon = new MemberCoupon(member, coupon);
        LocalDateTime expiresAt = memberCoupon.getIssueEndedAt();

        // then
        LocalDateTime expectedExpiresAt = now.plusDays(7)
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999_999_999);

        assertThat(expiresAt).isEqualToIgnoringNanos(expectedExpiresAt);
    }
}
