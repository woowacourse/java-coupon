package coupon.repository;

import coupon.domain.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisPooled;
import java.util.NoSuchElementException;

@Repository
@RequiredArgsConstructor
public class CouponInMemoryRepository {

    private static final Jackson2JsonRedisSerializer<Coupon> serializer = new Jackson2JsonRedisSerializer<>(Coupon.class);

    private final JedisPooled jedis;
    private final CouponRepository couponRepository;

    public Coupon getCoupon(Long id) {
        String couponJson = jedis.get(String.valueOf(id));

        if (couponJson == null) {
            Coupon coupon = couponRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("해당 쿠폰이 존재하지 않습니다. id = " + id));
            jedis.set(String.valueOf(id), couponToJson(coupon));
            return coupon;
        }

        return jsonToCoupon(couponJson);
    }

    protected void clearCache() {
        jedis.flushDB();
    }

    private String couponToJson(Coupon coupon) {
        return new String(serializer.serialize(coupon));
    }

    private Coupon jsonToCoupon(String couponJson) {
        return serializer.deserialize(couponJson.getBytes());
    }
}
