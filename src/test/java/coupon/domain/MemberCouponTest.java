package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    private Coupon coupon;
    private Member member;

    @BeforeEach
    void setUp() {
        this.coupon = new Coupon("쿠폰이름", 1_000, 30_000, Category.FASHION, LocalDateTime.now(),
                LocalDateTime.now());
        this.member = new Member("미아");
    }

    @Test
    @DisplayName("회원에게 발급된 쿠폰은 발급일 포함 7일 동안 사용 가능하다.")
    void createUseEndedAt() {
        MemberCoupon memberCoupon = new MemberCoupon(coupon, member, false);
        LocalDateTime useTime = LocalDateTime.now().plusDays(7);

        boolean isAvailable = memberCoupon.isAvailable(useTime);

        assertThat(isAvailable).isTrue();
    }

}
