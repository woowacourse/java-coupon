package coupon.coupon.business;

import coupon.coupon.domain.Coupon;
import coupon.coupon.exception.CouponErrorMessage;
import coupon.coupon.exception.CouponException;
import coupon.coupon.infrastructure.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final SourceExecutor sourceExecutor;

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(long id) {
        return couponRepository.findById(id)
                .orElseGet(() -> getCouponFromSource(id));
    }

    private Coupon getCouponFromSource(long id) {
        return sourceExecutor.execute(() ->
                couponRepository.findById(id)
                        .orElseThrow(() -> new CouponException(CouponErrorMessage.CANNOT_FIND_COUPON))
        );
    }
}
