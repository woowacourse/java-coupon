package coupon.service;

import coupon.data.repository.CouponRepository;
import coupon.data.CouponEntity;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponMapper;
import coupon.exception.CouponNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Cacheable(value = "couponService", key = "#coupon")
    @Transactional
    public CouponEntity create(Coupon coupon) {
        return couponRepository.save(CouponMapper.toEntity(coupon));
    }

    @Cacheable(value = "couponService", key = "#userId")
    @Transactional(readOnly = true)
    public CouponEntity findCoupon(long userId) {
        return couponRepository.findById(userId).orElseThrow(() -> new CouponNotFoundException(String.valueOf(userId)));
    }

    @Transactional(readOnly = true)
    public List<CouponEntity> findCoupons() {
        return couponRepository.findAll();
    }
}
