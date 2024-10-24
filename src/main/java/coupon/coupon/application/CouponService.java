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

    public static final String COUPON_CACHE_KEY = "coupon:%d";

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional(readOnly = true)
    @Cacheable(value = "coupon", key = "#couponId")
    public CouponResponse getCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("Coupon not found: " + couponId));

        CouponResponse response = couponMapper.toResponse(coupon);

        return response;
    }

    @Transactional(readOnly = true)
    public Map<Long, CouponResponse> getCouponMapEachById(List<Long> couponIds) {
        CachedCouponsResponse cachedCouponsResponse = findCouponsByIdsFromCache(couponIds);
        Map<Long, CouponResponse> couponMap = new HashMap<>(cachedCouponsResponse.cachedData());
        List<Long> notCachedIds = cachedCouponsResponse.notCachedIds();
        Map<Long, CouponResponse> couponsFromDB = getCouponsFromDB(notCachedIds);
        couponMap.putAll(couponsFromDB);

        return couponMap;
    }

    public CachedCouponsResponse findCouponsByIdsFromCache(List<Long> couponIds) {
        Map<Long, CouponResponse> cached = new HashMap<>();
        List<Long> notCachedIds = new ArrayList<>();

        List<String> keys = couponIds.stream()
                .map(COUPON_CACHE_KEY::formatted)
                .toList();
        List<Object> fromCache = redisTemplate.opsForValue().multiGet(keys);
        for (int i = 0; i < couponIds.size(); i++) {
            Long id = couponIds.get(i);
            Object data = fromCache.get(i);
            if (data != null) {
                cached.put(id, (CouponResponse) data);
                continue;
            }
            notCachedIds.add(id);
        }

        return new CachedCouponsResponse(cached, notCachedIds);
    }

    private Map<Long, CouponResponse> getCouponsFromDB(List<Long> couponIds) {
        Map<Long, CouponResponse> couponMap = new HashMap<>();

        List<Coupon> coupons = couponRepository.findAllByIdIn(couponIds);
        for (Coupon coupon : coupons) {
            CouponResponse response = couponMapper.toResponse(coupon);
            couponMap.put(coupon.getId(), response);
        }

        Map<String, CouponResponse> cachingData = new HashMap<>();
        for (Map.Entry<Long, CouponResponse> entry : couponMap.entrySet()) {
            String key = COUPON_CACHE_KEY.formatted(entry.getKey());
            CouponResponse value = entry.getValue();
            cachingData.put(key, value);
        }
        redisTemplate.opsForValue().multiSet(cachingData);

        return couponMap;
    }

    @Transactional
    public Long createCoupon(CreateCouponRequest request) {
        Coupon coupon = couponMapper.toCoupon(request);
        Coupon savedCoupon = couponRepository.save(coupon);

        String key = COUPON_CACHE_KEY.formatted(savedCoupon.getId());
        CouponResponse value = couponMapper.toResponse(savedCoupon);
        redisTemplate.opsForValue().set(key, value);

        return coupon.getId();
    }
}
