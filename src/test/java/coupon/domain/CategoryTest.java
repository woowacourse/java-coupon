package coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CategoryTest {

    @DisplayName("존재하지 않는 카테고의 경우 예외가 발생한다.")
    @Test
    void from() {
        assertThatThrownBy(() -> Category.from("NOT_EXISTS"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 카테고리입니다.");
    }
}
