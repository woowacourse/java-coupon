package member.domain;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HashedPasswordCreatorTest {

    @DisplayName("다른 salt값을 가진 해시화된 패스워드를 비교할 때 결과는 다르다.")
    @Test
    void compareHashedPassword() {

        // given
        String plainPassword = "somePassword";
        String salt = "salt";
        String otherSalt = "otherSalt";
        HashedPasswordCreator hashedPasswordCreator = new HashedPasswordCreator();
        Password password = hashedPasswordCreator.createPassword(plainPassword, salt);
        Password comparedPassword = hashedPasswordCreator.createPassword(plainPassword, otherSalt);
        boolean expected = false;

        // when
        boolean actual = password.isCorrect(comparedPassword);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
