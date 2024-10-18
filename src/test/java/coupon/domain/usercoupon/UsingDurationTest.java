package coupon.domain.usercoupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UsingDurationTest {

    @Test
    @DisplayName("발급된 쿠폰은 사용 만료일의 자정 전까지 사용 가능하다")
    void test() {
        LocalDateTime startDateTime = LocalDateTime.of(2024, 10, 18, 0, 0, 0);
        UsingDuration usingDuration = new UsingDuration(startDateTime);

        assertThat(usingDuration.getEndDateTime())
                .isEqualTo(startDateTime.plusDays(7).with(LocalTime.MAX));
    }
}
