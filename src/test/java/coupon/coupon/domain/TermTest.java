package coupon.coupon.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import coupon.coupon.CouponException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class TermTest {

    @DisplayName("종료일이 시작일보다 이전이면 예외가 발생한다.")
    @Test
    void cannotCreateIfEndIsBeforeStart() {
        // given
        LocalDate startAt = LocalDate.now();
        LocalDate endAt = LocalDate.now().minusDays(1);

        // when & then
        assertThatThrownBy(() -> new Term(startAt, endAt))
                .isInstanceOf(CouponException.class)
                .hasMessage("종료일이 시작일보다 앞설 수 없습니다.");
    }

    @DisplayName("시작일과 종료일의 시간은 각각 00:00:00.000 및 23:59:59.999로 설정된다.")
    @Test
    void termStartAndEndTimeAreSetProperly() {
        // given
        LocalDate startAt = LocalDate.of(2024, 10, 1);
        LocalDate endAt = LocalDate.of(2024, 10, 2);

        // when
        Term term = new Term(startAt, endAt);

        // then
        assertThat(term).extracting("startAt").isEqualTo(LocalDateTime.of(2024, 10, 1, 0, 0, 0, 0));
        assertThat(term).extracting("endAt").isEqualTo(LocalDateTime.of(2024, 10, 2, 23, 59, 59, 999_999_000));
    }
}
