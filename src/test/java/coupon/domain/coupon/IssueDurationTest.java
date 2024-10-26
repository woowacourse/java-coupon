package coupon.domain.coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import coupon.exception.CouponException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IssueDurationTest {

    @DisplayName("발급 기간을 생성한다.")
    @Test
    void create() {
        assertThatCode(() -> new IssueDuration(
                LocalDateTime.of(2024, 1, 1, 1, 0, 0),
                LocalDateTime.of(2024, 1, 1, 1, 0, 1)))
                .doesNotThrowAnyException();
    }

    @DisplayName("종료일이 시작일 보다 이후가 아니면 예외가 발생한다.")
    @Test
    void create_Fail() {
        assertThatCode(() -> new IssueDuration(
                LocalDateTime.of(2024, 1, 1, 1, 0, 0),
                LocalDateTime.of(2024, 1, 1, 1, 0, 0)))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("발급 기간인지를 확인한다.")
    @Test
    void isWithin() {
        IssueDuration issueDuration = new IssueDuration(
                LocalDateTime.of(2024, 1, 1, 1, 0, 0),
                LocalDateTime.of(2024, 1, 1, 1, 0, 1)
        );
        LocalDateTime issuanceTime = LocalDateTime.of(2024, 1, 1, 1, 0, 0);

        assertThat(issueDuration.isWithin(issuanceTime)).isTrue();
    }
}
