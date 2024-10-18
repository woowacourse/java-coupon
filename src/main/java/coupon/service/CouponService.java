package coupon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.Coupon;
import coupon.repository.CouponEntity;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponEntity create(final Coupon coupon) {
        final CouponEntity couponEntity = new CouponEntity(coupon);

        return couponRepository.save(couponEntity);
    }
}
