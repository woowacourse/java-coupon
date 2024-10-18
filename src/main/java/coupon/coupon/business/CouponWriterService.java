package coupon.coupon.business;

import coupon.coupon.domain.Coupon;
import coupon.coupon.infrastructure.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponWriterService {

    private final CouponRepository couponRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Coupon findCouponInWriter(long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("coupon could not be found. id = " + id));
    }
}
