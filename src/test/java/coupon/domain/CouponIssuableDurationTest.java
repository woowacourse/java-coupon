package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponIssuableDurationTest {

    @Test
    @DisplayName("쿠폰 발급 가능 기간을 생성한다.")
    void create() {
        LocalDate today = LocalDate.now();

        assertThatCode(() -> new CouponIssuableDuration(today, today))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("시작일은 종료일보다 이전이어야 한다.")
    void invalidDuration() {
        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minusDays(1);

        assertThatThrownBy(() -> new CouponIssuableDuration(now, yesterday))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작일은 종료일보다 이전이어야 합니다.");
    }

    @Test
    @DisplayName("만료기간이 지나면 쿠폰을 발급할 수 없다.")
    void cantIssue() {
        LocalDate today = LocalDate.now();
        LocalDate start = today.minusDays(2);
        LocalDate end = today.minusDays(1);
        CouponIssuableDuration issuableDuration = new CouponIssuableDuration(start, end);

        assertThat(issuableDuration.isIssuable()).isFalse();
    }

    @Test
    @DisplayName("만료기간이 지나지 않으면 쿠폰을 발급할 수 있다.")
    void canIssue() {
        LocalDate today = LocalDate.now();
        CouponIssuableDuration issuableDuration = new CouponIssuableDuration(today, today);

        assertThat(issuableDuration.isIssuable()).isTrue();
    }
}
