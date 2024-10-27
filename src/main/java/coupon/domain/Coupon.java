package coupon.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Coupon {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "discount_price_amount"))
    private Money discountPrice;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "minimum_order_price_amount"))
    private Money minimumOrderPrice;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    public Coupon(final String name, final Money discountPrice, final Money minimumOrderPrice, final Category category, final LocalDateTime startedAt, final LocalDateTime endAt) {
        this(null, name, discountPrice, minimumOrderPrice, category, startedAt, endAt);
    }

    public BigDecimal calculateDiscountRate() {
        final BigDecimal discountRate = minimumOrderPrice
                .divide(discountPrice)
                .divide(BigDecimal.valueOf(10));

        return discountRate.setScale(2, RoundingMode.DOWN);
    }
}
