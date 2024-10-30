package coupon.service;

import coupon.config.CouponCache;
import coupon.data.repository.CouponRepository;
import coupon.data.CouponEntity;
import coupon.domain.coupon.CouponMapper;
import coupon.exception.CouponNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public CouponEntity create(coupon.domain.coupon.Coupon coupon) {
        CouponCache.cache(CouponMapper.toEntity(coupon));
        return couponRepository.save(CouponMapper.toEntity(coupon));
    }

    @Transactional(readOnly = true)
    public CouponEntity findCoupon(long id) {
        return couponRepository.findById(id).orElseThrow(() -> new CouponNotFoundException(String.valueOf(id)));
    }

    @Transactional(readOnly = true)
    public List<CouponEntity> findCoupons() {
        return couponRepository.findAll();
    }
}
