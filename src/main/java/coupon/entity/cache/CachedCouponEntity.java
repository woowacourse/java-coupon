package coupon.entity.cache;

import coupon.domain.Coupon;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    public static CachedCouponEntity from(Coupon coupon) {
        return new CachedCouponEntity(coupon.getId(), coupon.getDiscountAmount(), coupon.getMinimumOrderPrice());
    }

    public Coupon toCoupon() {
        return new Coupon(id, discountAmount, minimumOrderPrice);
    }
}
