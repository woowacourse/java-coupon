package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @Test
    @DisplayName("회원에게 발급된 쿠폰은 발급일 포함 7일 동안 사용 가능")
    void issuedCouponPeriod() {
        // given
        var coupon = new Coupon();
        var member = new Member();
        var issuedAt = LocalDateTime.now();

        // when
        var sut = new MemberCoupon(coupon, member, issuedAt);
        var actual = sut.getExpireAt();

        // then
        var expected = issuedAt.plusDays(7);
        assertThat(actual).isEqualTo(expected);
    }
}
