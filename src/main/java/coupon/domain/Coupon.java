package coupon.domain;

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
import java.time.LocalTime;

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

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR")
    private Category category;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public Coupon(String name, int discountAmount, int minimumOrderAmount, Category category,
                  LocalDateTime startDate, LocalDateTime endDate) {
        validateName(name);
        validateDiscountAmount(discountAmount);
        validateMinimumOrderAmount(minimumOrderAmount);
        validateDiscountRate(discountAmount, minimumOrderAmount);
        validateIssuancePeriod(startDate, endDate);

        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
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
                .multiply(new BigDecimal(100));

        if (discountRate.compareTo(new BigDecimal(3)) < 0 || discountRate.compareTo(new BigDecimal(20)) > 0) {
            throw new IllegalArgumentException("할인율은 3% 이상 20% 이하이어야 합니다.");
        }
    }

    private void validateIssuancePeriod(LocalDateTime issuanceStart, LocalDateTime issuanceEnd) {
        if (issuanceStart == null || issuanceEnd == null || issuanceStart.isAfter(issuanceEnd)) {
            throw new IllegalArgumentException("발급 시작일은 종료일보다 이전이어야 합니다.");
        }
        validateStartTime(issuanceStart);
        validateEndTime(issuanceEnd);
    }

    private void validateStartTime(LocalDateTime startTime) {
        if (startTime.toLocalTime().isBefore(LocalTime.of(0, 0, 0, 0))) {
            throw new IllegalArgumentException("발급 시작일은 00:00:00부터 시작해야 합니다.");
        }
    }

    private void validateEndTime(LocalDateTime endTime) {
        if (endTime.toLocalTime().isAfter(LocalTime.of(23, 59, 59, 999999000))) {
            throw new IllegalArgumentException("발급 종료일은 23:59:59까지 종료해야 합니다.");
        }
    }

}
