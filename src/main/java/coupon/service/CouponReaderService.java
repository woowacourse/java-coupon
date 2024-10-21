package coupon.service;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponReaderService {

    private static final Logger log = LoggerFactory.getLogger(CouponReaderService.class);
    private final CouponRepository couponRepository;

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id, Supplier<Coupon> couponWriter) {
        log.info("Find coupon by id: {}", id);

        return couponRepository.findById(id)
                .orElseGet(couponWriter);
    }
}
