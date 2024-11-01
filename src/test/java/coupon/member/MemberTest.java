package coupon.member;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class MemberTest {

    @DisplayName("적절한 이름으로 쿠폰을 생성한다.")
    @ValueSource(strings = {"name", "n", "ThisStringIs20Length"})
    @ParameterizedTest
    void couponWithValidName(String name) {
        // when & then
        assertThatCode(() -> new Member(name)).doesNotThrowAnyException();
    }

    @DisplayName("이름이 존재하지 않으면 예외가 발생한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void exception_WhenNameIsNotExist(String name) {
        // when & then
        assertThatThrownBy(() -> new Member(name)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이름이 최대글자 초과시 예외가 발생한다.")
    @Test
    void exception_When_NameIsOverMaxLenght() {
        // given
        String tooLongName = "a".repeat(21);

        // when & then
        assertThatThrownBy(() -> new Member(tooLongName)).isInstanceOf(IllegalArgumentException.class);
    }
}
