package coupon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.Coupon;
import coupon.exception.CouponException;
import coupon.repository.CouponEntity;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponService {

    private final CouponReaderService couponReaderService;
    private final CouponWriterService couponWriterService;

    public CouponEntity create(final Coupon coupon) {
        return couponWriterService.create(coupon);
    }

    public CouponEntity getCoupon(final long id) {
        try {
            return couponReaderService.getCoupon(id);
        } catch (final CouponException e) {
            return couponWriterService.getCoupon(id);
        }
    }
}
