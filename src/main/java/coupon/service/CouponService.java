package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.repository.CouponRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Transactional
    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Optional<Coupon> findById(Long id) {
        return couponRepository.findById(id);
    }
}
