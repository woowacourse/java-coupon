package coupon.domain.member;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;

class MemberCouponTest {

    @Test
    void 만료날짜가_생성날짜보다_이전이면_예외가_발생한다() {
        Coupon coupon = new Coupon("test",
                Category.FASHION,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                10000,
                1000
        );
        assertThatThrownBy(
                () -> new MemberCoupon(coupon.getId(), false, LocalDateTime.now(), LocalDateTime.now().minusMinutes(1), new Member())
        ).isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("만료 날짜는 생성날짜 이전일 수 없습니다.");
    }
}
