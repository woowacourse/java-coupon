package coupon.application.coupon;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponWriteService {

    private final CouponRepository couponRepository;

    public CouponResponse generateNewCoupon(CouponGenerateRequest couponGenerateRequest) {
        Coupon coupon = couponRepository.save(couponGenerateRequest.toDomain());
        return CouponResponse.from(coupon);
    }
}
