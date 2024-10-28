package coupon.coupon.service.dto.request;

import java.time.LocalDateTime;

import coupon.coupon.domain.ProductionCategory;

public record CouponCreateRequest(
        String name,
        int minimumOrderAmount,
        int discountAmount,
        ProductionCategory productionCategory,
        LocalDateTime couponStartDate,
        LocalDateTime couponEndDate
) {}
