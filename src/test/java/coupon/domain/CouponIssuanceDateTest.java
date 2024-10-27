package coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CouponIssuanceDateTest {

    @Test
    @DisplayName("발급 시작일이 발급 종료일보다 늦은 경우 예외를 발생한다.")
    void validateIssuanceDateTest() {
        assertThatThrownBy(() -> new CouponIssuanceDate(LocalDateTime.now().plusDays(1), LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Issuance End Date cannot be earlier than Issuance Start Date");
    }
}
