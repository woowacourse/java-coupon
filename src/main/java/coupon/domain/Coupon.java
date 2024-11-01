package coupon.domain;

import java.time.LocalDate;
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CouponName name;

    @Column(name = "discount_amount", nullable = false)
    private long discountAmount;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "minimum_order_price", nullable = false)
    private long minimumOrderPrice;

    @Embedded
    private IssuePeriod issuePeriod;

    @Transient
    private DiscountPolicy discountPolicy;

    public Coupon(
            String name,
            long discountAmount,
            long minimumOrderPrice,
            Category category,
            LocalDate issueStartAt,
            LocalDate issueEndAt
    ) {
        this(
                null,
                new CouponName(name),
                discountAmount,
                category,
                minimumOrderPrice,
                new IssuePeriod(issueStartAt, issueEndAt),
                new DefaultDiscountPolicy(discountAmount, minimumOrderPrice));
        Objects.requireNonNull(category, "카테고리를 입력해야 합니다.");
        discountPolicy.validate();
    }
}
