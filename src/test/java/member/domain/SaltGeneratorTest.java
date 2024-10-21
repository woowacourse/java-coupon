package member.domain;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.member.domain.SaltGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SaltGeneratorTest {

    @DisplayName("생성된 salt는 빈값일 수 없다.")
    @Test
    void generateSaltIsNotEmpty() {
        // given
        SaltGenerator saltGenerator = new SaltGenerator();

        // when
        String salt = saltGenerator.generate();

        // then
        assertThat(salt).isNotEmpty();
    }

    @DisplayName("salt값은 매번 생성했을 때 같은 값이 생성되지 않는다.")
    @Test
    void generateSaltIsNotDuplicate() {
        // given
        SaltGenerator saltGenerator = new SaltGenerator();

        // when
        String salt = saltGenerator.generate();
        String otherSalt = saltGenerator.generate();

        // then
        assertThat(salt).isNotEqualTo(otherSalt);
    }
}
