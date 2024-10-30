package coupon.domain.membercoupon;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UsagePeriodTest {

    @DisplayName("회원에게 발급된 쿠폰은 발급일 포함 7일 동안 사용 가능하다.")
    @Test
    void calculateExpireDate() {
        UsagePeriod usagePeriod = new UsagePeriod(LocalDate.of(2024, 10, 1).atTime(0, 0));

        assertThat(usagePeriod.getExpireDate())
                .isEqualTo(LocalDate.of(2024, 10, 7).atTime(LocalTime.MAX));
    }
}
