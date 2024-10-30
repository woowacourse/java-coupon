package coupon.cache;

import coupon.domain.Coupon;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "coupon", timeToLive = 86_400)
public class CachedCoupon {

    @Id
    private Long id;

    private Coupon coupon;

    public CachedCoupon(Coupon coupon) {
        this.id = coupon.getId();
        this.coupon = coupon;
    }
}
