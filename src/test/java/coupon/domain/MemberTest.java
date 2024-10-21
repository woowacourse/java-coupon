package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import coupon.exception.CouponException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class MemberTest {

    @DisplayName("Member 생성 성공: 이름 글자수 경계값")
    @ParameterizedTest
    @ValueSource(strings = {"1", "12345678901234567890"})
    void construct_LegalName(String name) {
        assertDoesNotThrow(() -> new Member(name));
    }

    @DisplayName("Member 생성 실패: 이름 글자수 위반 경계값")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"123456789012345678901"})
    void construct_IllegalName(String name) {
        assertThatThrownBy(() -> new Member(name))
                .isInstanceOf(CouponException.class);
    }
}
