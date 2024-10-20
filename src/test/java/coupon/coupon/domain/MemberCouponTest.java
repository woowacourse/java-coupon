package coupon.coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.member.domain.Member;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class MemberCouponTest {

    @Mock
    private Member member;

    @Mock
    private Coupon coupon;

    @Test
    @DisplayName("만료일은 발급일로부터 7일 후 23:59:59.999999로 설정된다.")
    void checkExpirationDate() {
        MemberCoupon memberCoupon = new MemberCoupon(member, coupon);

        LocalDateTime issueAt = memberCoupon.getIssueAt();
        LocalDateTime expiredAt = memberCoupon.getExpiredAt();

        assertThat(expiredAt).isAfter(issueAt);
        assertThat(ChronoUnit.DAYS.between(issueAt, expiredAt)).isEqualTo(7);
        assertThat(expiredAt.getHour()).isEqualTo(23);
        assertThat(expiredAt.getMinute()).isEqualTo(59);
        assertThat(expiredAt.getSecond()).isEqualTo(59);
        assertThat(expiredAt.getNano()).isEqualTo(999999000);
    }
}
