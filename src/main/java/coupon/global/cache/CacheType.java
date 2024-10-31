package coupon.global.cache;

import lombok.Getter;

@Getter
public enum CacheType {

    COUPONS(CacheConstants.COUPON_CACHE_NAME, 60, 1000),
    ;

    private final String cacheName;
    private final int expiredAfterWrite;
    private final int maximumSize;

    CacheType(String cacheName, int expiredAfterWrite, int maximumSize) {
        this.cacheName = cacheName;
        this.expiredAfterWrite = expiredAfterWrite;
        this.maximumSize = maximumSize;
    }
}
