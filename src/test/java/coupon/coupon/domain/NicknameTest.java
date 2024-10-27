package coupon.coupon.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import coupon.coupon.CouponException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NicknameTest {

    @DisplayName("닉네임이 null일 경우 예외가 발생한다.")
    @Test
    void cannotCreateIfNull() {
        // given
        String nickname = null;

        // when & then
        assertThatThrownBy(() -> new Nickname(nickname))
                .isInstanceOf(CouponException.class)
                .hasMessage("회원명이 누락되었습니다.");
    }

    @DisplayName("닉네임이 비어있거나 공백이면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void cannotCreateIfBlank(String nickname) {
        assertThatThrownBy(() -> new Nickname(nickname))
                .isInstanceOf(CouponException.class)
                .hasMessage("회원명은 10자 이하로 설정해주세요.");
    }

    @DisplayName("닉네임이 10자를 초과하면 예외가 발생한다.")
    @Test
    void cannotCreateIfExceedMaxLength() {
        // given
        String nickname = "가".repeat(11);

        // when & then
        assertThatThrownBy(() -> new Nickname(nickname))
                .isInstanceOf(CouponException.class)
                .hasMessage("회원명은 10자 이하로 설정해주세요.");
    }
}
