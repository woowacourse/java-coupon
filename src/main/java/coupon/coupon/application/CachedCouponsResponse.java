package coupon.coupon.application;

import java.util.List;
import java.util.Map;

public record CachedCouponsResponse(Map<Long, CouponResponse> cachedData, List<Long> notCachedIds) {
}
