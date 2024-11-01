package coupon.service;

import coupon.repository.CouponRepository;
import coupon.repository.entity.Coupon;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponService {

    private static final Map<Long, Coupon> COUPON_CACHE = new ConcurrentHashMap<>();

    private final ReaderService readerService;
    private final CouponRepository couponRepository;

    @Transactional
    public Coupon saveCoupon(Coupon coupon) {
        Coupon saved = couponRepository.save(coupon);
        COUPON_CACHE.put(coupon.getId(), coupon);
        log.info("쿠폰 저장: {}", saved.getId());
        try {
            return readerService.read(() -> getCoupon(saved.getId()));
        } catch (IllegalArgumentException e) {
            log.error("쿠폰 조회 중 복제 지연 발생: {}", saved.getId());
            return getCoupon(saved.getId());
        }
    }

    public Coupon getCoupon(Long couponId) {
        log.info("쿠폰 조회: {}", couponId);
        return COUPON_CACHE.computeIfAbsent(couponId, id -> couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다: %d".formatted(couponId))));
    }
}
