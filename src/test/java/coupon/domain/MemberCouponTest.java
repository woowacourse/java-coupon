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
        var coupon = new Coupon(1L, new CouponName("우테코 수료 쿠폰"), 0L, null, 0L, null, null);
        var member = new Member(1L, new MemberName("초롱"));
        var issuedAt = LocalDateTime.now();

        // when
        var sut = new MemberCoupon(member, coupon, issuedAt);
        var actual = sut.getExpireAt();

        // then
        var expected = issuedAt.plusDays(7);
        assertThat(actual).isEqualTo(expected);
    }
}
