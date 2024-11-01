package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @Test
    @DisplayName("발급일이 null이면 예외를 발생시킨다.")
    void issueDateNullTest() {
        assertThatThrownBy(() -> new MemberCoupon(
                1L,
                1L,
                null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("발급일은 필수입니다.");
    }

    @Test
    @DisplayName("발급일로부터 7일 이후에 쿠폰을 사용하면 예외가 발생한다.")
    void useAfterExpireTest() {
        // given
        MemberCoupon memberCoupon = new MemberCoupon(
                1L,
                1L,
                LocalDate.now().minusDays(8L));

        // when & then
        assertThatThrownBy(() -> memberCoupon.use())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("쿠폰의 사용 기한이 만료되었습니다. 쿠폰은 발급일로부터 7일 동안 사용 가능합니다.");
    }

    @Test
    @DisplayName("발급일로부터 7일 이내에 쿠폰을 사용할 수 있다.")
    void useTest() {
        // given
        MemberCoupon memberCoupon = new MemberCoupon(
                1L,
                1L,
                LocalDate.now());

        // when
        assertThatCode(() -> memberCoupon.use()).doesNotThrowAnyException();

        // then
        assertThat(memberCoupon.isUsed()).isTrue();
    }
}
