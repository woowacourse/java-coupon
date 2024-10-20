package coupon.service;

import coupon.data.CouponRepository;
import coupon.data.Coupon;
import exception.CouponNotFound;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(long id) {
        return couponRepository.findById(id).orElseThrow(() -> new CouponNotFound(id));
    }

    @Transactional(readOnly = true)
    public List<Coupon> getCoupons() {
        return couponRepository.findAll();
    }
}
