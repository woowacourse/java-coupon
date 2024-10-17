package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    private int minimumOrderAmount;

    private int discountAmount;

    private int discountRate;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    private LocalDateTime issueStartDate;

    private LocalDateTime issueEndDate;

    public Coupon(String name, int minimumOrderAmount, int discountAmount, Category category,
                  LocalDateTime issueStartDate, LocalDateTime issueEndDate) {
        validateName(name);
        validateMinimumOrderAmount(minimumOrderAmount);
        validateDiscountAmount(discountAmount);
        validateDiscountRate(discountAmount, minimumOrderAmount);
        validateIssuePeriod(issueStartDate, issueEndDate);

        this.name = name;
        this.minimumOrderAmount = minimumOrderAmount;
        this.discountAmount = discountAmount;
        this.discountRate = calculateDiscountRate(discountAmount, minimumOrderAmount);
        this.category = category;
        this.issueStartDate = issueStartDate;
        this.issueEndDate = issueEndDate;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("쿠폰 이름은 필수입니다.");
        }
        if (name.length() > 30) {
            throw new IllegalArgumentException("쿠폰 이름은 30자 이하여야 합니다.");
        }
    }

    private void validateMinimumOrderAmount(int minimumOrderAmount) {
        if (minimumOrderAmount < 5000 || minimumOrderAmount > 100000) {
            throw new IllegalArgumentException("최소 주문 금액은 5,000원 이상, 100,000원 이하여야 합니다.");
        }
    }

    private void validateDiscountAmount(int discountAmount) {
        if (discountAmount < 1000 || discountAmount > 10000) {
            throw new IllegalArgumentException("할인 금액은 1,000원 이상, 10,000원 이하여야 합니다.");
        }
        if (discountAmount % 500 != 0) {
            throw new IllegalArgumentException("할인 금액은 500원 단위로 설정해야 합니다.");
        }
    }


    private void validateDiscountRate(int discountAmount, int minimumOrderAmount) {
        int discountRate = calculateDiscountRate(discountAmount, minimumOrderAmount);
        if (discountRate < 3 || discountRate > 20) {
            throw new IllegalArgumentException("할인율은 3% 이상, 20% 이하여야 합니다.");
        }
    }

    private int calculateDiscountRate(int discountAmount, int minimumOrderAmount) {
        return (discountAmount * 100) / minimumOrderAmount;
    }

    private void validateIssuePeriod(LocalDateTime issueStartDate, LocalDateTime issueEndDate) {
        if (issueStartDate == null || issueEndDate == null) {
            throw new IllegalArgumentException("발급 기간의 시작일과 종료일은 필수입니다.");
        }
        if (!issueStartDate.isBefore(issueEndDate) && !issueStartDate.isEqual(issueEndDate)) {
            throw new IllegalArgumentException("발급 시작일은 종료일보다 이전이어야 합니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coupon coupon = (Coupon) o;
        return Objects.equals(id, coupon.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
