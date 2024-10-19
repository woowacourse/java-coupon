package coupon.config;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CouponRoutingContext {

    private static final Map<Long, LocalDateTime> createdAtMap = new ConcurrentHashMap<>();
    private static final ThreadLocal<Long> currentCouponId = new ThreadLocal<>();

    private CouponRoutingContext() {

    }

    public static boolean isRecentlyCreated(long delaySeconds) {
        LocalDateTime createdAt = createdAtMap.get(currentCouponId.get());
        return createdAt != null && createdAt.plusSeconds(delaySeconds).isAfter(LocalDateTime.now());
    }

    public static void recordCouponCreatedAt(long couponId) {
        createdAtMap.put(couponId, LocalDateTime.now());
    }

    public static void setCurrentCouponId(long couponId) {
        currentCouponId.set(couponId);
    }

    public static void clearCurrentCouponId() {
        currentCouponId.remove();
    }
}
