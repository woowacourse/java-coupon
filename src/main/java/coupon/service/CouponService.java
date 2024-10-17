package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.dto.CouponCreateRequest;
import coupon.repository.CouponRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@AllArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public void createCoupon(CouponCreateRequest request) {
        Coupon coupon = request.toEntity();
        couponRepository.save(coupon);
    }
}
