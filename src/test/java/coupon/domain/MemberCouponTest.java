package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.exception.CouponConditionException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    private static final Coupon COUPON = CouponFixture.CREATED_COUPON;

    @Nested
    class ValidateTest {

        @Test
        void whenIssuedAtNull_throwException() {
            assertThatThrownBy(() -> new MemberCoupon(1L, COUPON, null))
                    .isInstanceOf(CouponConditionException.class)
                    .hasMessage("Member coupon must have all args.");
        }

        @Test
        void whenCouponNull_throwException() {
            assertThatThrownBy(() -> new MemberCoupon(1L, null, LocalDateTime.now()))
                    .isInstanceOf(CouponConditionException.class)
                    .hasMessage("Member coupon must have all args.");
        }

        @Test
        void whenMemberIdNull_throwException() {
            assertThatThrownBy(() -> new MemberCoupon(null, COUPON, LocalDateTime.now()))
                    .isInstanceOf(CouponConditionException.class)
                    .hasMessage("Member coupon must have all args.");
        }

        @Test
        void whenIssuedAtNotAllowed_throwException() {
            LocalDateTime issuedAtOutsideRange = LocalDateTime.of(2024, 12, 11, 0, 1);

            assertThatThrownBy(() -> new MemberCoupon(1L, COUPON, issuedAtOutsideRange))
                    .isInstanceOf(CouponConditionException.class)
                    .hasMessage("Member coupon must issue when allowed coupon condition.");
        }
    }

    @Test
    void calculateExpiredAtTest() {
        LocalDateTime issuedAt = LocalDateTime.of(2024, 12, 1, 10, 0);
        LocalDateTime expectedExpiredAt = LocalDateTime.of(LocalDate.of(2024, 12, 7), LocalTime.MAX);

        MemberCoupon memberCoupon = new MemberCoupon(1L, COUPON, issuedAt);

        assertThat(memberCoupon.getExpiredAt()).isEqualTo(expectedExpiredAt);
    }
}
