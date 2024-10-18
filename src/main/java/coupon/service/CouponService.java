package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.CouponRepository;
import coupon.service.support.DataSourceSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final DataSourceSupport dataSourceSupport;

    @Transactional
    public long create(Coupon coupon) {
        couponRepository.save(coupon);
        return coupon.getId();
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(long couponId) {
        return couponRepository.findById(couponId)
                .orElseGet(() -> findFromWriter(couponId));
    }

    private Coupon findFromWriter(long couponId) {
        return dataSourceSupport.executeOnWriter(
                () -> couponRepository.findById(couponId)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다. couponId: " + couponId))
        );
    }
}
