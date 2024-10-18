package coupon.service.coupon;

import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.discount.DiscountPolicy;
import coupon.domain.coupon.discount.DiscountType;
import coupon.domain.coupon.repository.CouponRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public Coupon createCoupon(String name, int discountPrice, int minOrderPrice,
                               DiscountType discountType, int minDiscountRange, int maxDiscountRange,
                               Category category, LocalDate issueStartDate, LocalDate issueEndDate) {
        DiscountPolicy discountPolicy = discountType.createDiscountPolicy(minDiscountRange, maxDiscountRange);
        Coupon coupon = new Coupon(name, discountPolicy, discountPrice, minOrderPrice, category, issueStartDate,
                issueEndDate);

        return couponRepository.save(coupon);
    }

    @Transactional
    public Coupon getById(long couponId) {
        return couponRepository.getById(couponId);
    }
}
