package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @Test
    void getExpirationDate() {
        MemberCoupon memberCoupon = new MemberCoupon(1L, 1L, LocalDate.now());

        assertAll(
                () -> assertThat(memberCoupon.getExpirationDate()).isEqualTo(LocalDate.now().plusDays(6)),
                () -> assertThat(memberCoupon.isUsed()).isEqualTo(false)
        );
    }
}
