package coupon.service;

import coupon.CouponException;
import coupon.dto.CouponCreateRequest;
import coupon.entity.Coupon;
import coupon.repository.CouponRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CacheManager cacheManager;

    @CachePut(key = "#result.id", value = "coupon")
    public Coupon create(CouponCreateRequest request) {
        Coupon coupon = new Coupon(
                request.name(),
                request.discount(),
                request.minimumOrder(),
                request.category(),
                request.start(),
                request.end()
        );
        return couponRepository.save(coupon);
    }

    public Coupon read(long id) {
        Optional<Cache> optionalCache = Optional.ofNullable(cacheManager.getCache("coupon"));
        return optionalCache.map(cache -> cache.get(id, Coupon.class))
                .or(() -> couponRepository.findById(id))
                .orElseThrow(() -> new CouponException("coupon with id " + id + " not found"));
    }
}
