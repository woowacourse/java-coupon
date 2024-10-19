package coupon.domain.coupon;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import coupon.domain.member.Member;
import coupon.fixture.CouponFixture;
import coupon.fixture.MemberFixture;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @DisplayName("멤버의 쿠폰은 발급일을 포함하여 7일 동안 사용할 수 있다.")
    @Test
    void issueEndDate() {
        // given
        Coupon coupon = CouponFixture.TEST_COUPON;
        Member member = MemberFixture.TEST_MEMBER;
        LocalDateTime now = LocalDateTime.now();

        // when
        MemberCoupon memberCoupon = new MemberCoupon(member, coupon);
        LocalDateTime actual = memberCoupon.getExpireAt();

        // then
        LocalDateTime expected = now.plusDays(7)
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999999);
        assertThat(actual).isEqualTo(expected);
    }
}
