package coupon.repository;

import coupon.domain.Coupon;
import coupon.service.exception.CouponBusinessLogicException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CouponRepository {

    private final CouponDiskRepository diskRepository;
    private final CouponMemoryRepository memoryRepository;

    public Coupon getById(Long id) {
        Optional<Coupon> cashedCoupon = memoryRepository.findById(id);
        if (cashedCoupon.isPresent()) {
            return cashedCoupon.get();
        }

        Optional<Coupon> coupon = diskRepository.findById(id);
        if (coupon.isPresent()) {
            saveAtMemory(coupon.get());
            return coupon.get();
        }

        throw new CouponBusinessLogicException("coupon not found");
    }

    public void save(Coupon coupon) {
        diskRepository.save(coupon);
        saveAtMemory(coupon);
    }

    private void saveAtMemory(Coupon coupon) {
        try {
            memoryRepository.save(coupon);
        } catch (RuntimeException e) {
            log.warn("쿠폰 캐시 저장 실패", e);
        }
    }
}
