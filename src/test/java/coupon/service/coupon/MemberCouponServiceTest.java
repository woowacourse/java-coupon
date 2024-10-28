package coupon.service.coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.entity.membercoupon.MemberCoupon;
import coupon.exception.membercoupon.CannotCreateMemberCouponException;
import coupon.service.membercoupon.MemberCouponService;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @Test
    void 회원에게_쿠폰을_발급할_수_있다() {
        // given
        MemberCoupon memberCoupon = new MemberCoupon(
                UUID.randomUUID().getMostSignificantBits(),
                UUID.randomUUID().getMostSignificantBits(),
                false,
                LocalDate.of(2000, 4, 7)
        );

        // when & then
        assertThat(memberCouponService.create(memberCoupon)).isNotNull();
    }

    @Test
    void 한명의_회원에게_동일한_쿠폰을_5장을_초과해_발급시_예외가_발생한다() {
        // given
        Long memberId = UUID.randomUUID().getMostSignificantBits();
        Long couponId = UUID.randomUUID().getMostSignificantBits();
        for (int i = 0; i < 5; i++) {
            MemberCoupon memberCoupon = new MemberCoupon(
                    memberId,
                    couponId,
                    false,
                    LocalDate.of(2000, 4, 7)
            );
            memberCouponService.create(memberCoupon);
        }
        MemberCoupon exceedingCoupon = new MemberCoupon(
                memberId,
                couponId,
                false,
                LocalDate.of(2000, 4, 7)
        );

        // when & then
        assertThatThrownBy(() -> memberCouponService.create(exceedingCoupon))
                .isInstanceOf(CannotCreateMemberCouponException.class);
    }
}
