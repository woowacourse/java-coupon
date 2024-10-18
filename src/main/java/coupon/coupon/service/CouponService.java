package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponRepository;
import coupon.coupon.dto.CouponCreateRequest;
import coupon.coupon.exception.CouponException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponException("쿠폰이 존재하지 않습니다."));
    }

    @Transactional
    public Coupon createCoupon(CouponCreateRequest couponRequest) {
        return couponRepository.save(couponRequest.toCouponEntity());
    }
}
