package coupon;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalDateTimeTest {
    private final LocalDateTime firstTime = LocalDateTime.of(2024, 10, 16, 0, 0, 0, 000_000_000);
    private final LocalDateTime lastTime = LocalDateTime.of(2024, 10, 16, 23, 59, 59, 999_999_999);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

    @Test
    @DisplayName("소수점 6자리 까지 표현이 가능하다.")
    void some() {
        assertThat(formatter.format(firstTime)).isEqualTo("2024-10-16 00:00:00.000000");
        assertThat(formatter.format(lastTime)).isEqualTo("2024-10-16 23:59:59.999999");
    }

    @Test
    @DisplayName("1 ns 를 더하면, 17 00:00:00:000000 이 된다.")
    void some1() {
        final LocalDateTime nextDayTime = lastTime.plusNanos(1);
        assertThat(formatter.format(nextDayTime)).isEqualTo("2024-10-17 00:00:00.000000");
    }

    @Test
    @DisplayName("1 ns 를 빼면, 15 23:59:59:999999 이 된다.")
    void some2(){
        final LocalDateTime prevDayTime = firstTime.minusNanos(1);
        assertThat(formatter.format(prevDayTime)).isEqualTo("2024-10-15 23:59:59.999999");
    }
}
