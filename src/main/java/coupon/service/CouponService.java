package coupon.service;

import coupon.domain.coupon.Coupon;
import coupon.repository.CouponRepository;
import coupon.service.support.DataSourceSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final DataSourceSupport dataSourceSupport;

    public CouponService(CouponRepository couponRepository, DataSourceSupport dataSourceSupport) {
        this.couponRepository = couponRepository;
        this.dataSourceSupport = dataSourceSupport;
    }

    @Transactional
    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .orElse(getCouponFromWriter(couponId));
    }

    private Coupon getCouponFromWriter(Long couponId) {
        return dataSourceSupport.executeOnWriter(() -> couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 쿠폰 id입니다.")));
    }
}
