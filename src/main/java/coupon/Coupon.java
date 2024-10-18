package coupon;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer discountAmount;

    @Column(nullable = false)
    private Integer purchaseAmount;

    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    public Coupon(
            Long id,
            String name,
            Integer discountAmount,
            Integer purchaseAmount,
            Category category,
            LocalDate startDate,
            LocalDate endDate
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
            LocalDate startDate,
            LocalDate endDate
    ) {
        this(null, name, discountAmount, purchaseAmount, category, startDate, endDate);
    }

    private void validate(
            String name,
            Integer discountAmount,
            Integer purchaseAmount,
            LocalDate startDate,
            LocalDate endDate
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

    private void validateIssuancePeriod(LocalDate startDate, LocalDate endDate) {
        if(startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이전이어야 합니다.");
        }
    }
}
