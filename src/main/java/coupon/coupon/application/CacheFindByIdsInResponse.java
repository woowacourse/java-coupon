package coupon.coupon.application;

import java.util.List;
import java.util.Map;

public record CacheFindByIdsInResponse<T>(Map<Long, T> cachedData, List<Long> notCachedIds) {
}
