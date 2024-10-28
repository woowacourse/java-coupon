package coupon.service;

import coupon.entity.Coupon;
import coupon.exception.CouponErrorMessage;
import coupon.exception.CouponException;
import coupon.repository.CouponRepository;
import coupon.validator.CouponValidator;
import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final FallbackReadService fallbackReadService;
    private final CouponRepository couponRepository;
    private final CouponValidator couponValidator;

    @Transactional
    public Coupon create(Coupon coupon) {
        couponValidator.validate(coupon);

        return couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(long couponId) {
        Callable<Coupon> callable = () -> couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponException(CouponErrorMessage.COUPON_NOT_FOUND));

        return couponRepository.findById(couponId)
                .orElseGet(() -> fallbackReadService.read(callable));
    }
}
