package coupon.service;

import coupon.domain.Coupon;
import coupon.exception.CouponException;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponWriteService {

    private final CouponRepository couponRepository;

    @Transactional
    public Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponException("쿠폰이 존재하지 않습니다."));
    }
}
