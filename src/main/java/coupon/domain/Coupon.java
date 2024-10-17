package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "discount_amount", nullable = false)
    private int discountAmount;

    @Column(name = "minimum_order_amount", nullable = false)
    private int minimumOrderAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    private Coupon(
            String name,
            int discountAmount,
            int minimumOrderAmount,
            Category category,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Coupon createCoupon(
            String name,
            int discountAmount,
            int minimumOrderAmount,
            Category category,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        Coupon coupon = new Coupon(name, discountAmount, minimumOrderAmount, category, startDate, endDate);
        validate(coupon);
        return coupon;
    }

    public static void validate(Coupon coupon) {
        validateName(coupon.getName());
        validateDiscountAmount(coupon.getDiscountAmount());
        validateMinimumOrderAmount(coupon.getMinimumOrderAmount());
        validateDiscountRate(coupon.getDiscountAmount(), coupon.getMinimumOrderAmount());
        validateEndDate(coupon.getStartDate(), coupon.getEndDate());
    }

    private static void validateName(String name) {
        if (name == null || name.isEmpty() || name.length() > 30) {
            throw new IllegalArgumentException("이름은 반드시 존재해야 하며, 최대 30자 이하여야 합니다");
        }
    }

    private static void validateDiscountAmount(int discountAmount) {
        if (discountAmount < 1000 || discountAmount > 10000 || discountAmount % 500 != 0) {
            throw new IllegalArgumentException("할인 금액은 1,000원 이상, 10,000원 이하이며 500원 단위로 설정해야 합니다");
        }
    }

    private static void validateMinimumOrderAmount(int minimumOrderAmount) {
        if (minimumOrderAmount < 5000 || minimumOrderAmount > 100000) {
            throw new IllegalArgumentException("최소 주문 금액은 5,000원 이상, 100,000원 이하여야 합니다");
        }
    }

    private static void validateDiscountRate(int discountAmount, int minimumOrderAmount) {
        double discountRate = Math.floor((double) discountAmount / minimumOrderAmount * 100);
        if (discountRate < 3 || discountRate > 20) {
            throw new IllegalArgumentException("할인율은 3% 이상, 20% 이하여야 합니다");
        }
    }

    private static void validateEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이전이어야 합니다");
        }
    }
}
