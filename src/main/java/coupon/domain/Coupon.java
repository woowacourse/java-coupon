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
                new IssuancePeriod(startDate, endDate)
        );
        Objects.requireNonNull(category, "카테고리를 입력해야 합니다.");
        validate(discountAmount, purchaseAmount);

    }

    private void validate(
            long discountAmount,
            long purchaseAmount
    ) {
        validateDiscountAmount(discountAmount);
        validatePurchaseAmount(purchaseAmount);
        validateDiscountRate(discountAmount, purchaseAmount);
    }

    private void validateDiscountAmount(long discountAmount) {
        if (discountAmount < 1_000 || discountAmount > 10_000) {
            throw new IllegalArgumentException("할인 금액은 1_000원 이상, 10_000원 이하여야 합니다.");
        }
        if (discountAmount % 500 != 0) {
            throw new IllegalArgumentException("할인 금액은 500원 단위로 설정할 수 있습니다.");
        }
    }

    private static void validatePurchaseAmount(long purchaseAmount) {
        if (purchaseAmount < 5_000 || purchaseAmount > 100_000) {
            throw new IllegalArgumentException("최소 주문 금액은 5_000원 이상 100_000원 이하여야 합니다.");
        }
    }

    private void validateDiscountRate(long discountAmount, long purchaseAmount) {
        int purchaseRate = (int) (discountAmount * 100 / purchaseAmount);
        if (purchaseRate < 3 || purchaseRate > 20) {
            throw new IllegalArgumentException("할인율은 3% 이상, 20% 이하여야 합니다.");
        }
    }
}
