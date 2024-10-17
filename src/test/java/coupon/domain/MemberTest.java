package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MemberTest {

    @ParameterizedTest
    @ValueSource(strings = {"", "0123465789012345678901234567891"})
    void 멤버_이름이_잘못된_경우_예외를_발생시킨다(String name) {
        assertThatThrownBy(() -> new Member(name))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

}
