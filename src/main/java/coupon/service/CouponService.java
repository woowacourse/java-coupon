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
public class CouponService {

    private final CouponRepository couponRepository;
    private final ReaderService readerService;

    @Transactional
    public Coupon saveCoupon(Coupon coupon) {
        Coupon saved = couponRepository.save(coupon);
        log.info("쿠폰 저장: {}", saved.getId());
        try {
            return readerService.read(() -> getCoupon(saved.getId()));
        } catch (IllegalArgumentException e) {
            log.error("쿠폰 조회 중 복제 지연 발생: {}", saved.getId());
            return getCoupon(saved.getId());
        }
    }

    @Transactional
    public Coupon getCoupon(Long couponId) {
        log.info("쿠폰 조회: {}", couponId);
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다: %d".formatted(couponId)));
    }
}
