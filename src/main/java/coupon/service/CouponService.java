package coupon.service;


import coupon.entity.Coupon;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }
}
