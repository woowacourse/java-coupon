package coupon.entity.cache;

import coupon.domain.Coupon;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "cached_coupon")
public class CachedCouponEntity implements Serializable {

    @Id
    private Long id;
    private int discountAmount;
    private int minimumOrderPrice;

    public CachedCouponEntity(Coupon coupon) {
        this.id = coupon.getId();
        this.discountAmount = coupon.getDiscountAmount();
        this.minimumOrderPrice = coupon.getMinimumOrderPrice();
    }
}
