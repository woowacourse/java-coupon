package coupon.coupon.domain;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import coupon.coupon.CouponException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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
}
