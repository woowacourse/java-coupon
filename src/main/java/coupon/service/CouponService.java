package coupon.service;

import coupon.domain.Coupon;
import coupon.repository.CouponReaderRepository;
import coupon.repository.CouponWriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponWriterRepository couponWriterRepository;
    private final CouponReaderRepository couponReaderRepository;

    @CachePut(value = "CouponCache", key = "#coupon.id")
    public Coupon create(Coupon coupon) {
        return couponWriterRepository.save(coupon);
    }

    @Cacheable(value = "CouponCache", key = "#id")
    public Coupon getCoupon(long id) {
        return couponReaderRepository.findById(id)
                .orElseGet(() -> getCouponByWriter(id));
    }

    public Coupon getCouponByWriter(long id) {
        return couponWriterRepository.findById(id)
                .orElseThrow();
    }
}
