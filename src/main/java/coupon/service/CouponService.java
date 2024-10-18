package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.dto.CouponCreateRequest;
import coupon.repository.CouponRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@AllArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public Coupon create(CouponCreateRequest request) {
        Coupon coupon = request.toEntity();
        return couponRepository.save(coupon);
    }

    public Coupon get(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 쿠폰이 존재하지 않습니다."));
    }
}
