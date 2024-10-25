package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import coupon.domain.Category;
import coupon.domain.member.Member;
import coupon.domain.member.MemberName;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    private Coupon coupon;
    private Member member;

    @BeforeEach
    void setUp() {
        coupon = new Coupon(
                new CouponName("쿠폰1"),
                new DiscountAmount("1000"),
                new MinimumOrderAmount("30000"),
                Category.ELECTRONICS,
                new IssueDuration(LocalDateTime.of(2024, 10, 1, 0, 0, 0), LocalDateTime.of(2024, 10, 31, 0, 0, 0))
        );

        member = new Member(new MemberName("wiib"));
    }

    @DisplayName("회원 쿠폰 생성한다.")
    @Test
    void create() {
        assertThatCode(() -> new MemberCoupon(coupon, member, LocalDateTime.of(2024, 10, 10, 0, 0, 0)))
                .doesNotThrowAnyException();
    }

    @DisplayName("회원 쿠폰 만료일자는 발급 일자를 포함한 7일 후 이다.")
    @Test
    void checkExpiredAt() {
        LocalDateTime issuedAt = LocalDateTime.of(2024, 10, 10, 12, 0, 0);
        MemberCoupon memberCoupon = new MemberCoupon(coupon, member, issuedAt);

        LocalDateTime actual = memberCoupon.getExpiredAt();
        LocalDateTime expected = LocalDateTime.of(2024, 10, 17, 0, 0, 0);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("처음 생성된 회원 쿠폰은 아직 미사용이다.")
    @Test
    void checkIsUsed() {
        LocalDateTime issuedAt = LocalDateTime.of(2024, 10, 10, 12, 0, 0);
        MemberCoupon memberCoupon = new MemberCoupon(coupon, member, issuedAt);

        assertThat(memberCoupon.isUsed()).isFalse();
    }
}
