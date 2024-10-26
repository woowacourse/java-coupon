package coupon.config.cache;

import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RedisKey {
    MEMBER_COUPON_COUNT("member:%d:coupon:%d:count"),
    COUPON("coupon:%d");

    private final String key;

    public String getKey(Object... args) {
        return key.formatted(args);
    }
}
