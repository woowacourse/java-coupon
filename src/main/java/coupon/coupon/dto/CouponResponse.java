package coupon.coupon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import coupon.coupon.domain.Coupon;
import java.time.LocalDateTime;

public record CouponResponse(
        long id,
        String name,
        int discountAmount,
        int minOrderAmount,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime startDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime endDate,
        double discountRate,
        String category
) {

    public static CouponResponse of(Coupon coupon) {
        return new CouponResponse(
                coupon.getId(),
                coupon.getName(),
                coupon.getDiscountAmount(),
                coupon.getMinOrderAmount(),
                coupon.getStartDate(),
                coupon.getEndDate(),
                coupon.calculateDiscountRate(),
                coupon.getCategory().name()
        );
    }
}
