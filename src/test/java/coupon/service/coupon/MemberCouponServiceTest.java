package coupon.service.coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.entity.coupon.Coupon;
import coupon.entity.coupon.CouponCategory;
import coupon.entity.membercoupon.MemberCoupon;
import coupon.exception.membercoupon.CannotCreateMemberCouponException;
import coupon.service.membercoupon.MemberCouponResponse;
import coupon.service.membercoupon.MemberCouponService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private CouponService couponService;

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

    @Test
    void 회원에게_발급된_쿠폰의_정보를_조회할_수_있다() throws InterruptedException {
        // given
        LocalDateTime stamp = LocalDateTime.now();
        String rightName = "발급" + stamp;
        Coupon coupon = new Coupon(
                rightName,
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(10000),
                CouponCategory.FOOD,
                LocalDate.of(2000, 4, 7),
                LocalDate.of(2000, 4, 12)
        );
        couponService.create(coupon);

        Long memberId = UUID.randomUUID().getMostSignificantBits();
        Long couponId = coupon.getId();
        for (int i = 0; i < 5; i++) {
            MemberCoupon memberCoupon = new MemberCoupon(
                    memberId,
                    couponId,
                    false,
                    LocalDate.of(2000, 4, 7)
            );
            memberCouponService.create(memberCoupon);
        }

        // when
        Thread.sleep(2000); // 복제 지연 대기
        List<MemberCouponResponse> response = memberCouponService.getMemberCoupons(memberId);

        // then
        assertThat(response.size()).isEqualTo(5);
        assertThat(response.get(0).couponResponse().name()).isEqualTo(rightName);
    }
}
