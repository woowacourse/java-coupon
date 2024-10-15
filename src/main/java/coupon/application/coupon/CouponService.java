package coupon.application.coupon;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponRepository;
import coupon.exception.CouponException;
import coupon.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new CouponException(ExceptionType.COUPON_NOT_FOUND));
    }
}
