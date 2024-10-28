package coupon.config;

import coupon.domain.coupon.Coupon;
import coupon.service.Cache;
import coupon.service.LRUCouponCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfiguration {

    private static final int COUPON_CACHE_SIZE = 100;

    @Bean
    public Cache<Long, Coupon> couponCache() {
        return new LRUCouponCache<>(COUPON_CACHE_SIZE);
    }
}
