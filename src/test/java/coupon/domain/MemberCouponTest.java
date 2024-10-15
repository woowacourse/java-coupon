package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @Test
    @DisplayName("회원 쿠폰이 정상 생성된다.")
    void createMemberCoupon() {
        // when & then
        assertThatCode(() -> new MemberCoupon(1L, 1L, false, LocalDateTime.now(), LocalDateTime.now()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("회원 쿠폰이 정상 발급된다.")
    void issue() {
        // given
        LocalDate date = LocalDate.now();
        Coupon coupon = new Coupon(1L, "쿠폰", 1_000, 30_000, CouponCategory.FASHION, date, date);

        // when
        MemberCoupon memberCoupon = MemberCoupon.issue(1L, coupon);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(memberCoupon.isUsed()).isFalse();
            softly.assertThat(memberCoupon.getExpiredAt()).isEqualTo(coupon.getIssueEndedAt().plusDays(7));
        });
    }
}
