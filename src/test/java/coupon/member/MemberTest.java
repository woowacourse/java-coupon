package coupon.member;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.coupon.exception.CouponApplicationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MemberTest {

    private static final String VALID_NAME = "리비";

    @DisplayName("유효한 회원 생성")
    @Test
    void createValidMember() {
        assertThatCode(() -> new Member(1L, VALID_NAME))
                .doesNotThrowAnyException();
    }

    @DisplayName("회원 이름이 Null인 경우 예외 발생")
    @Test
    void createMemberWithNullName() {
        assertThatThrownBy(() -> new Member(null))
                .isInstanceOf(CouponApplicationException.class)
                .hasMessage("멤버의 이름은 비어있을 수 없습니다");
    }

    @DisplayName("회원 이름이 빈 문자열인 경우 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    void createMemberWithEmptyName(String invalidName) {
        assertThatThrownBy(() -> new Member(invalidName))
                .isInstanceOf(CouponApplicationException.class)
                .hasMessage("멤버의 이름은 비어있을 수 없습니다");
    }

    @DisplayName("회원 이름의 길이가 최소 길이보다 작은 경우 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"리", "도", "아"})
    void createMemberWithShortName(String invalidName) {
        assertThatThrownBy(() -> new Member(invalidName))
                .isInstanceOf(CouponApplicationException.class)
                .hasMessage("멤버 이름의 길이는 2이상, 10이하여야 합니다");
    }

    @DisplayName("회원 이름의 길이가 최대 길이를 초과하는 경우 예외 발생")
    @Test
    void createMemberWithLongName() {
        final var longNameLength11 = "일이삼사오육칠팔구십일";
        assertThatThrownBy(() -> new Member(longNameLength11))
                .isInstanceOf(CouponApplicationException.class)
                .hasMessage("멤버 이름의 길이는 2이상, 10이하여야 합니다");
    }
}
