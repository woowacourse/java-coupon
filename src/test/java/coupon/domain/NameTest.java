package coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NameTest {

    @Test
    @DisplayName("쿠폰의 이름은 반드시 존재해야 한다.")
    void create() {
        Name name = new Name("coupon");

        assertThat(name).isEqualTo(new Name("coupon"));
    }
}
