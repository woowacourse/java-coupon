package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.dto.CouponRequest;
import coupon.coupon.dto.CouponResponse;
import coupon.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponService {

    private final CouponReader couponReader;
    private final CouponRepository couponRepository;

    @Transactional
    public CouponResponse create(CouponRequest request) {
        Coupon coupon = couponRepository.save(request.toEntity());
        return CouponResponse.from(coupon);
    }

    public Coupon getCoupon(long couponId) {
        return couponRepository.findById(couponId)
                .orElseGet(() -> couponReader.getCouponFromWriteDb(couponId));
    }
}
