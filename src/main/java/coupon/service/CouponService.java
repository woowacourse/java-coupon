package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.repository.CouponRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public Optional<Coupon> findById(Long id) {
        return couponRepository.findById(id);
    }
}
