package coupon.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.CouponName;
import coupon.domain.DiscountAmount;
import coupon.domain.IssuancePeriod;
import coupon.domain.MinOrderAmount;
import coupon.exception.CouponException;
import coupon.repository.CouponEntity;
import coupon.repository.CouponRepository;

@SpringBootTest
@Transactional
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;
    @Autowired
    private CouponRepository couponRepository;

    @Test
    @DisplayName("한 명의 회원은 동일한 쿠폰을 최대 5장까지 발급 가능하다.")
    void issuedCouponLimit5() {
        //given
        final long memberId = 1L;

        //TODO fixture 로 빼기
        final CouponName name = new CouponName("레디레디");
        final DiscountAmount discountAmount = new DiscountAmount(5000);
        final MinOrderAmount minOrderAmount = new MinOrderAmount(5000);
        final IssuancePeriod issuancePeriod = new IssuancePeriod(LocalDate.now(), LocalDate.now().plusDays(3));
        final Category category = Category.FASHION;
        final Coupon coupon = new Coupon(name, discountAmount, minOrderAmount, issuancePeriod, category);

        final CouponEntity couponEntity = couponRepository.save(new CouponEntity(coupon));
        final long couponId = couponEntity.getId();

        //when
        for (int i = 0; i < 5; i++) {
            memberCouponService.issuedCoupon(couponId, memberId);
        }

        //then
        assertThatThrownBy(() -> memberCouponService.issuedCoupon(couponId, memberId))
                .isInstanceOf(CouponException.class);
    }
}
