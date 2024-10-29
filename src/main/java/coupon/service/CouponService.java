package coupon.service;


import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    @CachePut(value = "coupon", key = "#result.id")
    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "coupon", key = "#id")
    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("id가 " + id + "인 쿠폰이 존재하지 않습니다."));
    }
}
