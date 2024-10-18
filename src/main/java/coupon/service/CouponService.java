package coupon.service;

import coupon.domain.Coupon;
import coupon.dto.CouponSaveRequest;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public Coupon save(CouponSaveRequest couponSaveRequest) {
        Coupon coupon = couponSaveRequest.toCoupon();
        return couponRepository.save(coupon);
    }
}
