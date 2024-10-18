package coupon;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {

    private CouponRepository couponRepository;

    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id).orElse(null);
    }
}
