package coupon.config.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RedisKey {
    MEMBER_COUPON("member:%d:coupon:%d"),
    MEMBER_COUPONS("member:%d:coupons"),
    COUPON("coupon:%d");

    private final String key;

    public String getKey(Object... args) {
        return key.formatted(args);
    }
}
