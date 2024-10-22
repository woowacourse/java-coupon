package coupon.application;

import coupon.domain.Coupon;
import coupon.domain.CouponRepository;
import coupon.dto.CouponResponse;
import coupon.replication.ReplicationLag;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public Long create(Coupon coupon) {
        Coupon savedCoupon = couponRepository.save(coupon);
        return savedCoupon.getId();
    }

    @ReplicationLag
    @Transactional(readOnly = true)
    public CouponResponse getCouponByAdmin(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 존재하지 않습니다."));

        return CouponResponse.from(coupon);
    }

    @ReplicationLag
    @Cacheable(value = "coupons", key = "#couponId")
    @Transactional(readOnly = true)
    public CouponResponse getCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 존재하지 않습니다."));

        return CouponResponse.from(coupon);
    }
}
