package coupon.repository;

import coupon.domain.Coupon;
import coupon.service.exception.CouponBusinessLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponRepository {

    private final CouponDiskRepository diskRepository;

    public Coupon getById(Long id) {
        return diskRepository.findById(id)
                .orElseThrow(() -> new CouponBusinessLogicException("Coupon not found ID = " + id));
    }

    public void save(Coupon coupon) {
        diskRepository.save(coupon);
    }
}
