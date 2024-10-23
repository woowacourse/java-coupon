package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PeriodOfIssuanceTest {

    @Test
    @DisplayName("발급 기간이 잘 생성된다.")
    void periodOfIssuance() {
        assertAll(
                () -> assertDoesNotThrow(() -> new PeriodOfIssuance(LocalDate.now(), LocalDate.now())),
                () -> assertDoesNotThrow(() -> new PeriodOfIssuance(LocalDate.now(), LocalDate.now().plusDays(1)))
        );
    }

    @Test
    @DisplayName("날짜 정보가 없을 경우 예외가 발생한다.")
    void validateNotNull() {
        assertAll(
                () -> assertThatThrownBy(() -> new PeriodOfIssuance(null, LocalDate.now()))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("날짜 정보가 존재하지 않습니다."),
                () -> assertThatThrownBy(() -> new PeriodOfIssuance(LocalDate.now(), null))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("날짜 정보가 존재하지 않습니다."),
                () -> assertThatThrownBy(() -> new PeriodOfIssuance(null, null))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("날짜 정보가 존재하지 않습니다.")
        );
    }

    @Test
    @DisplayName("시작일이 종료일 이후일 경우 예외가 발생한다.")
    void validateOrder() {
        assertThatThrownBy(() -> new PeriodOfIssuance(LocalDate.now(), LocalDate.now().minusDays(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작일은 종료일 이전이어야 합니다.");
    }
}
