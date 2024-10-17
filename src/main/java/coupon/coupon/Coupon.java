package coupon.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int discountAmount;

    private int minimumOrderAmount;

    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private LocalDateTime issuanceStart;

    @Column(nullable = false)
    private LocalDateTime issuanceEnd;

    public Coupon(String name, int discountAmount, int minimumOrderAmount, Category category,
                  LocalDateTime issuanceStart, LocalDateTime issuanceEnd) {
        validateName(name);
        validateDiscountAmount(discountAmount);
        validateMinimumOrderAmount(minimumOrderAmount);
        validateDiscountRate(discountAmount, minimumOrderAmount);
        validateIssuancePeriod(issuanceStart, issuanceEnd);

        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.category = category;
        this.issuanceStart = issuanceStart;
        this.issuanceEnd = issuanceEnd;
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty() || name.length() > 30) {
            throw new IllegalArgumentException("쿠폰 이름은 필수이며 최대 30자 이하이어야 합니다.");
        }
    }

    private void validateDiscountAmount(int discountAmount) {
        if (discountAmount < 1000 || discountAmount > 10000 || discountAmount % 500 != 0) {
            throw new IllegalArgumentException("할인 금액은 1,000원 이상 10,000원 이하이며 500원 단위여야 합니다.");
        }
    }

    private void validateMinimumOrderAmount(int minimumOrderAmount) {
        if (minimumOrderAmount < 5000 || minimumOrderAmount > 100000) {
            throw new IllegalArgumentException("최소 주문 금액은 5,000원 이상 100,000원 이하여야 합니다.");
        }
    }

    private void validateDiscountRate(int discountAmount, int minimumOrderAmount) {
        BigDecimal discountRate = new BigDecimal(discountAmount)
                .divide(new BigDecimal(minimumOrderAmount), 2, RoundingMode.DOWN)
                .multiply(new BigDecimal(100));  // 백분율 계산

        if (discountRate.compareTo(new BigDecimal(3)) < 0 || discountRate.compareTo(new BigDecimal(20)) > 0) {
            throw new IllegalArgumentException("할인율은 3% 이상 20% 이하이어야 합니다.");
        }
    }

    private void validateIssuancePeriod(LocalDateTime issuanceStart, LocalDateTime issuanceEnd) {
        if (issuanceStart == null || issuanceEnd == null || issuanceStart.isAfter(issuanceEnd)) {
            throw new IllegalArgumentException("발급 시작일은 종료일보다 이전이어야 합니다.");
        }
    }
}
