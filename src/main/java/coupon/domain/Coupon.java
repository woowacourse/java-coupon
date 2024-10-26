package coupon.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "discount_amount", nullable = false)
    private Integer discountAmount;

    @Column(name = "purchase_amount", nullable = false)
    private Integer purchaseAmount;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    public Coupon(
            Long id,
            String name,
            Integer discountAmount,
            Integer purchaseAmount,
            Category category,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        validate(name, discountAmount, purchaseAmount, startDate, endDate);
        this.id = id;
        this.name = name;
        this.discountAmount = discountAmount;
        this.purchaseAmount = purchaseAmount;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Coupon(
            String name,
            Integer discountAmount,
            Integer purchaseAmount,
            Category category,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        this(null, name, discountAmount, purchaseAmount, category, startDate, endDate);
    }

    private void validate(
            String name,
            Integer discountAmount,
            Integer purchaseAmount,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        validateName(name);
        validateDiscountAmount(discountAmount);
        validatePurchaseAmount(purchaseAmount);
        validateDiscountRate(discountAmount, purchaseAmount);
        validateIssuancePeriod(startDate, endDate);
    }

    private void validateName(String name) {
        if (name == null || name.isBlank() || name.length() > 30) {
            throw new IllegalArgumentException("쿠폰의 이름은 1자 이상 30자 이하여야 합니다.");
        }
    }

    private void validateDiscountAmount(Integer discountAmount) {
        if (discountAmount == null || discountAmount < 1_000 || discountAmount > 10_000) {
            throw new IllegalArgumentException("할인 금액은 1_000원 이상, 10_000원 이하여야 합니다.");
        }
        if (discountAmount % 500 != 0) {
            throw new IllegalArgumentException("할인 금액은 500원 단위로 설정할 수 있습니다.");
        }
    }

    private static void validatePurchaseAmount(Integer purchaseAmount) {
        if (purchaseAmount == null || purchaseAmount < 5_000 || purchaseAmount > 100_000) {
            throw new IllegalArgumentException("최소 주문 금액은 5_000원 이상 100_000원 이하여야 합니다.");
        }
    }

    private void validateDiscountRate(Integer discountAmount, Integer purchaseAmount) {
        int purchaseRate = discountAmount * 100 / purchaseAmount;
        if (purchaseRate < 3 || purchaseRate > 20) {
            throw new IllegalArgumentException("할인율은 3% 이상, 20% 이하여야 합니다.");
        }
    }

    private void validateIssuancePeriod(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이전이어야 합니다.");
        }
    }
}
