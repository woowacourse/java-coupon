package coupon.coupon.application;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponRepository;
import coupon.coupon.domain.Coupons;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CouponService {

    private static final String CACHE_NAME = "coupon";

    private final CouponRepository couponRepository;

    @CachePut(value = CACHE_NAME, key = "#result.id", unless = "#result == null")
    public Coupon create(final Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = CACHE_NAME, key = "#id", unless = "#result == null")
    public Coupon getCoupon(final Long id) {
        return couponRepository.fetchById(id);
    }

    @Transactional(readOnly = true)
    public Coupons getAllByCouponsIdIn(List<Long> ids) {;
        return ids.stream()
                .map(this::getCoupon)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Coupons::new));
    }
}
