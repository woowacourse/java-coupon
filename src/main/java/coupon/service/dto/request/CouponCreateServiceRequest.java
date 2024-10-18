package coupon.service.dto.request;

import java.time.LocalDateTime;

public record CouponCreateServiceRequest(String couponName,
                                         long discountAmount,
                                         LocalDateTime start,
                                         LocalDateTime end) {
}
