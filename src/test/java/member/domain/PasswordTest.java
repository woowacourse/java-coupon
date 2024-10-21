package member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import coupon.member.domain.Password;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PasswordTest {

    @DisplayName("패스워드가 빈 값이거나 Null일 경우 예외가 발생한다.")
    @Test
    void throwException_nameIsNullOrEmpty() {
        assertAll(
                () -> assertThatThrownBy(() -> new Password(null))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> new Password(""))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @DisplayName("두 패스워드를 비교할 수 있다.")
    @CsvSource({"password, true", "incorrectPassword, false"})
    @ParameterizedTest
    void isCorrect(String givenPassword, boolean expected) {
        // given
        Password password = new Password("password");
        Password comparedPassword = new Password(givenPassword);

        // when
        boolean actual = password.isCorrect(comparedPassword);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
