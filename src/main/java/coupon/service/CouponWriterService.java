package coupon.service;

import coupon.repository.CouponRepository;
import coupon.repository.entity.Coupon;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponWriterService {

    private final CouponRepository couponRepository;
    private final CouponReaderService couponReaderService;

    @Transactional
    public Coupon saveCouponBefore(Coupon coupon) {
        Coupon saved = couponRepository.save(coupon);
        log.info("쿠폰 저장: {}", saved.getId());
        return saved;
    }

    @Transactional
    public Coupon saveCouponAfter(Coupon coupon) {
        Coupon saved = couponRepository.save(coupon);
        log.info("쿠폰 저장 후 조회: {}", saved.getId());
        try {
            return couponReaderService.getCoupon(saved.getId());
        } catch (IllegalArgumentException e) {
            log.error("저장 중 복제 지연 발생: 쿠폰 {}", saved.getId());
            return getCoupon(saved.getId());
        }
    }

    @Transactional
    public Coupon getCoupon(Long couponId) {
        log.info("writer DB에서 쿠폰 조회: {}", couponId);
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다: %d".formatted(couponId)));
    }
}
