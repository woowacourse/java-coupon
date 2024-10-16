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
import jakarta.persistence.Transient;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = "id")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Name name;

    @Column(nullable = false)
    private long discountAmount;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
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
                new Name(name),
                discountAmount,
                category,
                minimumOrderPrice,
                new IssuePeriod(issueStartAt, issueEndAt),
                new DefaultDiscountPolicy());
        Objects.requireNonNull(category, "카테고리를 입력해야 합니다.");
        discountPolicy.validate(discountAmount, minimumOrderPrice);
    }
}
