package coupon.service;

import coupon.repository.CouponRepository;
import coupon.repository.entity.Coupon;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponReaderService {

    private final CouponRepository couponRepository;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public Coupon getCoupon(Long couponId) {
        log.info("reader DB에서 쿠폰 조회: {}", couponId);
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다: %d".formatted(couponId)));
    }
}
