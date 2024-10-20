package coupon.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

    private final String defaultName = "상돌";
    private final String defaultEmail = "abc@abc.com";
    private final String defaultPassword = "1234";


    @DisplayName("이름은 Null일 수 없다.")
    @Test
    void nullName() {
        assertThrows(IllegalArgumentException.class, () -> Member.builder()
                .name(null)
                .email(defaultEmail)
                .password(defaultPassword)
                .build()
        );
    }

    @DisplayName("이름은 빈 값일 수 없다.")
    @Test
    void emptyName() {
        assertThrows(IllegalArgumentException.class, () -> Member.builder()
                .name("")
                .email(defaultEmail)
                .password(defaultPassword)
                .build()
        );
    }

    @DisplayName("이메일은 Null일 수 없다.")
    @Test
    void nullEmail() {
        assertThrows(IllegalArgumentException.class, () -> Member.builder()
                .name(defaultName)
                .email(null)
                .password(defaultPassword)
                .build()
        );
    }

    @DisplayName("이메일은 빈 값일 수 없다.")
    @Test
    void emptyEmail() {
        assertThrows(IllegalArgumentException.class, () -> Member.builder()
                .name(defaultName)
                .email("")
                .password(defaultPassword)
                .build()
        );
    }

    @DisplayName("비밀번호는 Null일 수 없다.")
    @Test
    void nullPassword() {
        assertThrows(IllegalArgumentException.class, () -> Member.builder()
                .name(defaultName)
                .email(defaultEmail)
                .password(null)
                .build()
        );
    }

    @DisplayName("비밀번호는 빈 값일 수 없다.")
    @Test
    void emptyPassword() {
        assertThrows(IllegalArgumentException.class, () -> Member.builder()
                .name(defaultName)
                .email(defaultEmail)
                .password("")
                .build()
        );
    }
}
