package coupon.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CouponName name;

    @Column(name = "discount_amount", nullable = false)
    private long discountAmount;

    @Column(name = "purchase_amount", nullable = false)
    private long purchaseAmount;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Embedded
    private IssuancePeriod issuancePeriod;

    @Transient
    private PricePolicy pricePolicy;

    public Coupon(
            String name,
            long discountAmount,
            long purchaseAmount,
            Category category,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        this(
                null,
                new CouponName(name),
                discountAmount,
                purchaseAmount,
                category,
                new IssuancePeriod(startDate, endDate),
                new PricePolicy()
        );
        Objects.requireNonNull(category, "카테고리를 입력해야 합니다.");
        pricePolicy.validate(discountAmount, purchaseAmount);
    }
}
