package coupon.coupon.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private static final String COUPON_CACHE_NAME = "coupon";
    private static final String COUPON_CACHE_FORMAT = "coupon::%d";

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;
    private final RedisTemplate<String, CouponResponse> redisTemplate;

    @Transactional(readOnly = true)
    @Cacheable(value = COUPON_CACHE_NAME, key = "#couponId")
    public CouponResponse getCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("Coupon not found: " + couponId));

        return couponMapper.toResponse(coupon);
    }

    @Transactional
    @CachePut(value = COUPON_CACHE_NAME, key = "#result")
    public CouponResponse createCoupon(CreateCouponRequest request) {
        Coupon coupon = couponMapper.toCoupon(request);
        Coupon savedCoupon = couponRepository.save(coupon);

        return couponMapper.toResponse(savedCoupon);
    }

    public Map<Long, CouponResponse> getCouponResponseMap(List<Long> couponIds) {
        Map<Long, CouponResponse> couponResponseMap = new HashMap<>();
        List<String> keys = couponIds.stream()
                .map(COUPON_CACHE_FORMAT::formatted)
                .toList();
        List<CouponResponse> cachedCouponResponses = redisTemplate.opsForValue().multiGet(keys);

        List<Long> notCachedCouponIds = new ArrayList<>();
        for (int i = 0; i < couponIds.size(); i++) {
            Long couponId = couponIds.get(i);
            CouponResponse couponResponse = cachedCouponResponses.get(i);
            if (couponResponse != null) {
                couponResponseMap.put(couponId, couponResponse);
                continue;
            }
            notCachedCouponIds.add(couponId);
        }

        if (!notCachedCouponIds.isEmpty()) {
            List<Coupon> coupons = couponRepository.findAllByIdIn(notCachedCouponIds);
            for (Coupon coupon : coupons) {
                CouponResponse couponResponse = couponMapper.toResponse(coupon);
                couponResponseMap.put(coupon.getId(), couponResponse);
                redisTemplate.opsForValue().set(COUPON_CACHE_FORMAT.formatted(coupon.getId()), couponResponse);
            }
        }

        return couponResponseMap;
    }
}
