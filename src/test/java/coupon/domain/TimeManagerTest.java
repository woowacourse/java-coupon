package coupon.domain;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TimeManagerTest {

    @DisplayName("같은 날에는 발급이 가능하다")
    @Test
    void isEffective() {
        TimeManager timeManager = new TimeManager(LocalDate.of(2024, 10, 22), LocalDate.of(2024, 10, 22));
        Assertions.assertThat(timeManager.isIssuable()).isTrue();
    }
}
