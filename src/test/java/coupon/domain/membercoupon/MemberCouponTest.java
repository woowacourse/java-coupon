package coupon.domain.membercoupon;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MemberCouponTest {

    @ParameterizedTest
    @CsvSource(value = {
            "2024-10-14T00:00:00, 2024-10-20T23:59:59.999999",
            "2024-10-14T23:59:59, 2024-10-20T23:59:59.999999"
    })
    void 멤버_쿠폰을_생성하면_만료_일은_발급_일을_포함한_7일_이후이다(LocalDateTime issuedAt, LocalDateTime expiredAt) {
        // when
        MemberCoupon memberCoupon = new MemberCoupon(issuedAt, 1L, 1L);

        // then
        assertThat(memberCoupon.getExpiredAt()).isEqualTo(expiredAt);
    }
}
