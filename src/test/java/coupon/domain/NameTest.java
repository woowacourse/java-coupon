package coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class NameTest {

    @DisplayName("최대 길이를 초과하는 경우 예외가 발생한다.")
    @Test
    void fail_invalidateNameLength() {
        String couponName = "카피 반갑습니다 쿠폰. 유효기간은 오늘까지입니다. 감사요";

        assertAll(
                () -> assertThat(couponName.length()).isEqualTo(31),
                () -> assertThatThrownBy(() -> new Name(couponName))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("최대 이름 길이를 초과합니다.")
        );
    }
}
