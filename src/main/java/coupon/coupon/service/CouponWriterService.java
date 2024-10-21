package coupon.coupon.service;

import coupon.coupon.entity.CouponEntity;
import coupon.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponWriterService {

    private final CouponRepository couponRepository;

    public CouponEntity getCoupon(long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow();
    }

    public CouponEntity create(CouponEntity coupon) {
        return couponRepository.save(coupon);
    }
}
