package coupon.application;

import coupon.domain.Coupon;
import coupon.domain.CouponCache;
import coupon.domain.CouponRepository;
import coupon.replication.ReplicationLag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponCache couponCache;

    @Transactional
    public void create(Coupon coupon) {
        Coupon savedCoupon = couponRepository.save(coupon);
        couponCache.save(savedCoupon);
    }

    @ReplicationLag
    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 존재하지 않습니다."));
    }
}
