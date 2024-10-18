package coupon.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    @Test
    @DisplayName("쿠폰이 null이면 예외를 발생시킨다.")
    void couponNullTest() {
        assertThatThrownBy(() -> new MemberCoupon(null, new Member("사용자"), LocalDate.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰은 필수입니다.");
    }

    @Test
    @DisplayName("사용자가 null이면 예외를 발생시킨다.")
    void memberNullTest() {
        assertThatThrownBy(() -> new MemberCoupon(
                new Coupon("쿠폰", 5_000L, 1_000L, LocalDate.now(), LocalDate.now(), new Category("카테고리")), null,
                LocalDate.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("사용자는 필수입니다.");
    }

    @Test
    @DisplayName("발급일이 null이면 예외를 발생시킨다.")
    void issueDateNullTest() {
        assertThatThrownBy(() -> new MemberCoupon(
                new Coupon("쿠폰", 5_000L, 1_000L, LocalDate.now(), LocalDate.now(), new Category("카테고리")),
                new Member("사용자"), null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("발급일은 필수입니다.");
    }
}
