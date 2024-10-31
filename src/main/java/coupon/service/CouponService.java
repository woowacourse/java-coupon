package coupon.service;

import coupon.aop.Cacheable;
import coupon.aop.ReplicationDelayHandler;
import coupon.domain.Coupon;
import coupon.domain.dto.CouponCreateRequest;
import coupon.repository.CouponRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public Coupon create(CouponCreateRequest request) {
        Coupon coupon = request.toEntity();
        return couponRepository.save(coupon);
    }

    @ReplicationDelayHandler
    @Cacheable
    public Coupon get(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 쿠폰이 존재하지 않습니다."));
    }
}
