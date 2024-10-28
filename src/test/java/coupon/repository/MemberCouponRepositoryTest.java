package coupon.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.CouponName;
import coupon.domain.DiscountAmount;
import coupon.domain.IssuancePeriod;
import coupon.domain.MinOrderAmount;

@SpringBootTest
class MemberCouponRepositoryTest {

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    @DisplayName("memberCoupon을 쿠폰id와 멤버id로 카운트한다.")
    void countByCouponIdAndMemberId() {
        //given
        final CouponName name = new CouponName("레디레디");
        final DiscountAmount discountAmount = new DiscountAmount(5000);
        final MinOrderAmount minOrderAmount = new MinOrderAmount(5000);
        final IssuancePeriod issuancePeriod = new IssuancePeriod(LocalDate.now(), LocalDate.now().plusDays(3));
        final Category category = Category.FASHION;
        final Coupon coupon = new Coupon(name, discountAmount, minOrderAmount, issuancePeriod, category);

        final CouponEntity couponEntity = couponRepository.save(new CouponEntity(coupon));

        final long memberId = 1L;

        final int savedCount = 3;

        //when
        for (int i = 0; i < savedCount; i++) {
            memberCouponRepository.save(new MemberCouponEntity(couponEntity, memberId));
        }

        //then
        assertThat(memberCouponRepository.countMemberCouponEntitiesByMemberIdAndCoupon(memberId, couponEntity))
                .isEqualTo(savedCount);
    }
}
