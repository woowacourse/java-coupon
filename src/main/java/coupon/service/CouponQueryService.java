package coupon.service;

import coupon.repository.CouponRepository;
import coupon.domain.Coupon;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CouponQueryService {

    private final CouponRepository couponRepository;
    private final CouponCommandService couponCommandService;

    public CouponQueryService(CouponRepository couponRepository, CouponCommandService couponCommandService) {
        this.couponRepository = couponRepository;
        this.couponCommandService = couponCommandService;
    }

    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id).orElseGet(()-> couponCommandService.getCoupon(id));
    }
}
