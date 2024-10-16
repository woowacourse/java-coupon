package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @Test
    void getExpirationDate() {
        Coupon coupon = new Coupon("couponName", 2_000, 15_000, "가구", LocalDate.now(), LocalDate.now());
        Member member = new Member();
        MemberCoupon memberCoupon = new MemberCoupon(coupon, member, LocalDate.now());

        assertAll(
                () -> assertThat(memberCoupon.getExpirationDateTime()).isEqualTo(LocalDate.now().plusDays(6)),
                () -> assertThat(memberCoupon.isUsed()).isEqualTo(false)
        );
    }
}
