package coupon.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final RedisTemplate<String, Coupon> redisTemplate;

    public Coupon create(Coupon coupon) {
        Coupon savedCoupon = couponRepository.save(coupon);
        redisTemplate.opsForValue().set("coupon:" + coupon.getId(), savedCoupon);
        return savedCoupon;
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(long id) {
        Coupon cachedCoupon = redisTemplate.opsForValue().get("coupon:" + id);
        if (cachedCoupon != null) {
            return cachedCoupon;
        }
        Coupon coupon = couponRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("id에 해당하는 쿠폰이 존재하지 않습니다."));
        redisTemplate.opsForValue().set("coupon:" + id, coupon);
        return coupon;
    }
}
