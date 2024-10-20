package coupon.config;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CouponRoutingContext {

    private static final Map<Long, LocalDateTime> createdAtById = new ConcurrentHashMap<>();
    private static final ThreadLocal<Long> currentCouponId = new ThreadLocal<>();

    private CouponRoutingContext() {

    }

    public static boolean isRecentlyCreated(long delaySeconds) {
        LocalDateTime createdAt = createdAtById.get(currentCouponId.get());
        return createdAt != null && createdAt.plusSeconds(delaySeconds).isAfter(LocalDateTime.now());
    }

    public static void recordCouponCreatedAt(long couponId) {
        createdAtById.put(couponId, LocalDateTime.now());
    }

    public static void setCurrentCouponId(long couponId) {
        currentCouponId.set(couponId);
    }

    public static void clearCurrentCouponId() {
        currentCouponId.remove();
    }
}
