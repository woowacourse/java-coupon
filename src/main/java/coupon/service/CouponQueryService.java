package coupon.service;

import coupon.repository.CouponRepository;
import coupon.domain.Coupon;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CouponQueryService {

    private final CouponRepository couponRepository;
    private final CouponService couponService;

    public CouponQueryService(CouponRepository couponRepository, CouponService couponService) {
        this.couponRepository = couponRepository;
        this.couponService = couponService;
    }

    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id).orElseGet(()->couponService.getCoupon(id));
    }
}
