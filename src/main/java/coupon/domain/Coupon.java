package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private int discountAmount;

    @Column(nullable = false)
    private int minimumOrderAmount;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM", nullable = false)
    private Category category;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private int availableCount; // 발급 가능한 쿠폰 수량

    @OneToMany(mappedBy = "coupon")
    private List<MemberCoupon> memberCoupons;

    public Coupon(
            Long id,
            String name,
            int discountAmount,
            int minimumOrderAmount,
            Category category,
            LocalDateTime startDate,
            LocalDateTime endDate,
            int availableCount
    ) {
        validateCoupon(name, discountAmount, minimumOrderAmount, category, startDate, endDate, availableCount);
        this.id = id;
        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.availableCount = availableCount;
    }

    public Coupon(
            String name,
            int discountAmount,
            int minimumOrderAmount,
            Category category,
            LocalDateTime startDate,
            LocalDateTime endDate,
            int availableCount
    ) {
        this(null, name, discountAmount, minimumOrderAmount, category, startDate, endDate, availableCount);
    }

    private void validateCoupon(
            String name,
            int discountAmount,
            int minimumOrderAmount,
            Category category,
            LocalDateTime startDate,
            LocalDateTime endDate,
            int availableCount
    ) {
        validateName(name);
        validateDiscount(discountAmount, minimumOrderAmount);
        validateCategory(category);
        validateDate(startDate, endDate);
        validateAvailableCount(availableCount);
    }

    private void validateAvailableCount(int availableCount) {
        if (availableCount < 0) {
            throw new IllegalArgumentException("발급 가능한 쿠폰 수량은 0 이상이어야 합니다.");
        }
    }

    private void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("쿠폰 이름은 반드시 존재해야 합니다.");
        }
        if (name.length() > 30) {
            throw new IllegalArgumentException("쿠폰 이름은 최대 30자 이하여야 합니다.");
        }
    }

    private void validateDiscount(int discountAmount, int minimumOrderAmount) {
        if (discountAmount < 1000 || discountAmount > 10000) {
            throw new IllegalArgumentException("할인 금액은 1,000원 이상 10,000원 이하여야 합니다.");
        }
        if (discountAmount % 500 != 0) {
            throw new IllegalArgumentException("할인 금액은 500원 단위로 설정할 수 있습니다.");
        }

        if (minimumOrderAmount < 5000 || minimumOrderAmount > 100000) {
            throw new IllegalArgumentException("최소 주문 금액은 5,000원 이상 100,000원 이하여야 합니다.");
        }

        double discountRate = (double) discountAmount / minimumOrderAmount * 100;
        discountRate = Math.floor(discountRate);
        if (discountRate < 3 || discountRate > 20) {
            throw new IllegalArgumentException("할인율은 3% 이상 20% 이하여야 합니다.");
        }
    }

    private void validateCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("카테고리는 반드시 선택해야 합니다.");
        }
    }

    private void validateDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작일은 종료일 이전일 수 없습니다.");
        }
    }
}
