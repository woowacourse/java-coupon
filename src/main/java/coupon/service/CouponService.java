package coupon.service;

import coupon.domain.Coupon;
import coupon.repository.CouponReaderRepository;
import coupon.repository.CouponWriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponWriterRepository couponWriterRepository;
    private final CouponReaderRepository couponReaderRepository;

    public Coupon create(Coupon coupon) {
        return couponWriterRepository.save(coupon);
    }

    public Coupon getCoupon(long id) {
        return couponReaderRepository.findById(id)
                .orElseGet(() -> getCouponByWriter(id));
    }

    public Coupon getCouponByWriter(long id) {
        return couponWriterRepository.findById(id)
                .orElseThrow();
    }
}
