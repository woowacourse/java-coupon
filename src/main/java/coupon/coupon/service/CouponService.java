package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponServiceWithWriter couponServiceWithWriter;

    @CachePut(value = "coupon", key = "#coupon.id")
    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Cacheable(value = "coupon", key = "#id")
    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        try {
            return couponRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            return couponServiceWithWriter.getCoupon(id);
        }
    }
}
