package coupon.service;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponWriterService {

    private static final Logger log = LoggerFactory.getLogger(CouponWriterService.class);
    private final CouponRepository couponRepository;

    @Transactional
    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Coupon getCouponWithWriter(Long id) {
        log.info("Find coupon by id: {}", id);

        return couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Coupon not found."));
    }
}
