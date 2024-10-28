package coupon.repository;

import coupon.domain.Coupon;
import coupon.service.exception.CouponBusinessLogicException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
            memoryRepository.save(coupon.get());
            return coupon.get();
        }

        throw new CouponBusinessLogicException("coupon not found");
    }

    public void save(Coupon coupon) {
        diskRepository.save(coupon);
    }
}
