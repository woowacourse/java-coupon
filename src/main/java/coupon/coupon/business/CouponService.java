package coupon.coupon.business;

import coupon.coupon.domain.Coupon;
import coupon.coupon.infrastructure.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponWriterService couponWriterService;

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(long id) {
        Optional<Coupon> coupon = couponRepository.findById(id);
        if(coupon.isEmpty()) {
            coupon = Optional.ofNullable(couponWriterService.findCouponInWriter(id));
        }
        return coupon.orElse(null);
    }
}
