package coupon.repository;

import coupon.domain.Coupon;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CouponCacheRepository {

    private final ConcurrentHashMap<UUID, Coupon> MAP = new ConcurrentHashMap<>();

    public Optional<Coupon> findById(final UUID id) {
        return Optional.ofNullable(MAP.get(id));
    }

    public Coupon getById(final UUID id) {
        final Coupon entity = MAP.get(id);
        if (entity == null) {
            throw new IllegalArgumentException(String.format("%s 에 해당하는 쿠폰이 없습니다.", id));
        }
        return entity;
    }

    public Coupon save(final Coupon coupon) {
        return MAP.put(coupon.getId(), coupon);
    }

    public boolean contains(final UUID id) {
        return MAP.containsKey(id);
    }
}
