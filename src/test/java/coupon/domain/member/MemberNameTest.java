package coupon.domain.member;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberNameTest {


    @Test
    @DisplayName("null 값으로 이름을 생성할 경우 예외로 처리한다.")
    void nullName() {
        String nullSource = null;
        assertThatThrownBy(() -> new MemberName(nullSource))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 비어있을 수 없습니다.");
    }

    @Test
    @DisplayName("이름이 0자인 경우 예외로 처리한다.")
    void zeroLength() {
        String emptyName = "";
        assertThatThrownBy(() -> new MemberName(emptyName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 비어있을 수 없습니다.");
    }

    @Test
    @DisplayName("이름이 30자를 초과하면 예외로 처리한다.")
    void mountAboveMaximum() {
        String tooLongName = "이무송은포케이무송은포케이무송은포케이무송은포케이무송은포케이무송은포케이무송은포케이무송은포케이무송은포케";
        assertThatThrownBy(() -> new MemberName(tooLongName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 최대 30자 입니다.");
    }
}
