package coupon.member;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import coupon.member.domain.Member;
import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    void 회원_생성() {
        // given
        String name = "name";

        // when, then
        assertDoesNotThrow(() -> new Member(name));
    }

    @Test
    void 회원_이름이_없을_경우_예외() {
        // given
        String emptyName = "";
        String nullName = null;

        // when, then
        assertAll(
                () -> assertThatThrownBy(() -> new Member(emptyName)).isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> new Member(nullName)).isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 회원_이름이_30를_넘을_경우_예외() {
        // given
        String longName = "a".repeat(31);

        // when, then
        assertThatThrownBy(() -> new Member(longName)).isInstanceOf(IllegalArgumentException.class);
    }
}
