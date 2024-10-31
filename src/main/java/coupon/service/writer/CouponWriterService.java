package coupon.service.writer;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponWriterService {

    private static final Logger log = LoggerFactory.getLogger(CouponWriterService.class);

    private final CouponRepository couponRepository;

    @Transactional
    @CachePut(cacheNames = "coupon", key = "#coupon.id")
    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Cacheable(cacheNames = "coupon", key = "#id")
    public Coupon getCouponWithWriter(Long id) {
        log.info("Find coupon by id: {}", id);

        return couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Coupon not found."));
    }
}
