package coupon.domain.member;

import static org.assertj.core.api.Assertions.assertThatCode;

import coupon.exception.CouponException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class MemberNameTest {

    @DisplayName("회원명을 생성한다.")
    @Test
    void create() {
        assertThatCode(() -> new MemberName("wiib"))
                .doesNotThrowAnyException();
    }

    @DisplayName("회원명이 비어있으면 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void create_Fail(String name) {
        assertThatCode(() -> new MemberName(name))
                .isInstanceOf(CouponException.class);
    }
}
