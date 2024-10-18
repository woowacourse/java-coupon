package coupon.membercoupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class UsablePeriodTest {

    @Test
    @DisplayName("회원에게 발급된 쿠폰은 발급일 포함 7일 동안 사용 가능하다.")
    void calculateExpirationDate() {
        LocalDate IssuanceDate = LocalDate.of(2024, 10, 10);
        UsablePeriod usablePeriod = new UsablePeriod(IssuanceDate);

        assertThat(usablePeriod).isEqualTo(new UsablePeriod(IssuanceDate, LocalDate.of(2024, 10, 16)));
    }
}
