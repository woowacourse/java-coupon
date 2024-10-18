package coupon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.Coupon;
import coupon.repository.CouponEntity;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponWriterService {

    private final CouponRepository couponRepository;

    public CouponEntity create(final Coupon coupon) {
        final CouponEntity couponEntity = new CouponEntity(coupon);

        return couponRepository.save(couponEntity);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CouponEntity getCoupon(final long id) {
        return couponRepository.fetchById(id);
    }
}
