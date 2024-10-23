package coupon.coupon.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional(readOnly = true)
    public List<CouponResponse> getCoupons() {
        List<Coupon> coupons = couponRepository.findAll();

        return couponMapper.toResponses(coupons);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "coupon", key = "#couponId")
    public CouponResponse getCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다. id: %d".formatted(couponId)));

        return couponMapper.toResponse(coupon);
    }

    @Transactional(readOnly = true)
    public Map<Long, Coupon> getCouponMapEachById(List<Long> couponIds) {
        CacheFindByIdsInResponse<Coupon> cacheFindByIdsInResponse = findByIdsIn("coupon", couponIds);
        Map<Long, Coupon> couponMap = new HashMap<>(cacheFindByIdsInResponse.cachedData());
        List<Long> notCachedIds = cacheFindByIdsInResponse.notCachedIds();
        Map<Long, Coupon> couponsFromDB = getCouponsFromDB(notCachedIds);
        couponMap.putAll(couponsFromDB);

        return couponMap;
    }

    public <T> CacheFindByIdsInResponse<T> findByIdsIn(String value, List<Long> ids) {
        Map<Long, T> cached = new HashMap<>();
        List<Long> notCachedIds = new ArrayList<>();

        List<String> keys = ids.stream()
                .map(id -> "%s:%d".formatted(value, id))
                .toList();
        List<Object> fromCache = redisTemplate.opsForValue().multiGet(keys);
        for (int i = 0; i < ids.size(); i++) {
            Long id = ids.get(i);
            Object data = fromCache.get(i);
            if (data != null) {
                cached.put(id, (T) data);
                continue;
            }
            notCachedIds.add(id);
        }

        return new CacheFindByIdsInResponse<>(cached, notCachedIds);
    }

    private Map<Long, Coupon> getCouponsFromDB(List<Long> couponIds) {
        Map<Long, Coupon> couponMap = new HashMap<>();

        List<Coupon> coupons = couponRepository.findAllByIdIn(couponIds);

        return couponMap;
    }

    @Transactional
    public Long createCoupon(CreateCouponRequest request) {
        Coupon coupon = couponMapper.toCoupon(request);
        Coupon savedCoupon = couponRepository.save(coupon);

        String key = "coupon:%d".formatted(savedCoupon.getId());
        CouponResponse cachingData = couponMapper.toResponse(savedCoupon);
        redisTemplate.opsForValue().set(key, cachingData);

        return coupon.getId();
    }
}
