package coupon.dto;

import java.time.LocalDateTime;

public record CouponRequest(String name, int minimumOrderAmount, int discountAmount,
                            String category, LocalDateTime issueStartDate, LocalDateTime issueEndDate) {
}
