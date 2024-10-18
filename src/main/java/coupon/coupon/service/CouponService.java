package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponCategory;
import coupon.coupon.domain.CouponEntity;
import coupon.coupon.repository.CouponRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponBackupService couponBackupService;

    private final CouponDiscountAmountValidator couponDiscountAmountValidator;
    private final CouponMinOrderAmountValidator couponMinOrderAmountValidator;

    @Transactional
    public long createCoupon(String couponName, int couponDiscountAmount, int couponMinOrderAmount,
                             CouponCategory couponCategory,
                             LocalDateTime couponStartDate, LocalDateTime couponEndDate) {
        couponDiscountAmountValidator.validate(couponDiscountAmount, couponMinOrderAmount);
        couponMinOrderAmountValidator.validate(couponDiscountAmount);

        Coupon coupon = new Coupon(
                couponName, couponDiscountAmount, couponMinOrderAmount, couponCategory, couponStartDate, couponEndDate
        );

        CouponEntity couponEntity = couponRepository.save(CouponEntity.mapDomainToEntity(coupon));
        return couponEntity.getId();
    }

    @Transactional(readOnly = true)
    public CouponEntity getCoupon(long couponId) {
        return couponRepository.findById(couponId)
                .orElseGet(() -> couponBackupService.getCouponFromWriter(couponId));
    }
}
