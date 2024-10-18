package coupon.domain.member;

import coupon.common.ErrorConstant;
import coupon.common.exception.CouponException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberNameTest {

    @Test
    @DisplayName("이름이 비어있는 경우 에러를 발생한다.")
    void memberName_WhenEmpty() {
        assertThatThrownBy(() -> new MemberName(" "))
                .isInstanceOf(CouponException.class)
                .hasMessage(ErrorConstant.MEMBER_NAME_IS_NULL_OR_EMPTY.getMessage());
    }
}
