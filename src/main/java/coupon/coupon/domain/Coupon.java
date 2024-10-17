package coupon.coupon.domain;

import java.time.LocalDateTime;

import coupon.coupon.domain.Category;
import lombok.Getter;

@Getter
public class Coupon {

    private final String name;
    private final Integer discountPrice;
    private final Integer minimumOrderPrice;
    private final Integer discountPercent;
    private final Category category;
    private final LocalDateTime issuedAt;
    private final LocalDateTime expiresAt;

    public Coupon(String name, int discountPrice, int minimumOrderPrice, Category category) {
        this.name = name;
        this.discountPrice = discountPrice;
        this.minimumOrderPrice = minimumOrderPrice;
        this.discountPercent = discountPrice / minimumOrderPrice;
        this.category = category;
        this.issuedAt = LocalDateTime.now();
        this.expiresAt = LocalDateTime.now();
    }
}
