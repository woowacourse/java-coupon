package coupon.coupon;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import coupon.coupon.dto.CouponCreateRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public enum CouponFixture {

    TOUROOT_COUPON(
            null,
            "투룻 쿠폰",
            BigDecimal.valueOf(1000),
            BigDecimal.valueOf(10000),
            Category.FASHION,
            LocalDateTime.now(),
            LocalDateTime.now().plusDays(3)
    );

    private final Long id;
    private final String name;
    private final BigDecimal discountAmount;
    private final BigDecimal minimumOrderAmount;
    private final Category category;
    private final LocalDateTime issuedAt;
    private final LocalDateTime expiredAt;

    CouponFixture(
            final Long id,
            final String name,
            final BigDecimal discountAmount,
            final BigDecimal minimumOrderAmount,
            final Category category,
            final LocalDateTime issuedAt,
            final LocalDateTime expiredAt
    ) {
        this.id = id;
        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.category = category;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
    }

    public Coupon getCoupon() {
        return new Coupon(id, name, discountAmount, minimumOrderAmount, category, issuedAt, expiredAt);
    }

    public CouponCreateRequest getCouponCreateRequest() {
        return new CouponCreateRequest(name, discountAmount, minimumOrderAmount, category.name(), issuedAt, expiredAt);
    }
}
