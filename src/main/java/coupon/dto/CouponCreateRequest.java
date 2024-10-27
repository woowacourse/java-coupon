package coupon.dto;

import coupon.domain.Category;
import coupon.domain.Coupon;
import java.time.LocalDateTime;

public class CouponCreateRequest {

    String name;
    Integer discount;
    Integer discountRate;
    Integer minOrderPrice;
    String category;
    LocalDateTime issuableFrom;
    LocalDateTime issuableTo;

    public CouponCreateRequest(String name, Integer discount, Integer discountRate, Integer minOrderPrice,
                               String category, LocalDateTime issuableFrom, LocalDateTime issuableTo) {
        this.name = name;
        this.discount = discount;
        this.discountRate = discountRate;
        this.minOrderPrice = minOrderPrice;
        this.category = category;
        this.issuableFrom = issuableFrom;
        this.issuableTo = issuableTo;
    }

    public Coupon toEntity() {
        return new Coupon(
                name,
                discount,
                discountRate,
                minOrderPrice,
                Category.valueOf(category),
                issuableFrom,
                issuableTo
        );
    }
}
