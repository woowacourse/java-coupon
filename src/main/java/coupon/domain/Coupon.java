package coupon.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "coupon")
@Getter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "discount_amount")
    private int discountAmount;

    @Column(name = "min_order_amount")
    private int minOrderAmount;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Column(name = "issuance_start_date")
    private LocalDateTime issuanceStartDate;

    @Column(name = "issuance_end_date")
    private LocalDateTime issuanceEndDate;

    protected Coupon() {
    }

    public Coupon(Long id, String name, int discountAmount, int minOrderAmount, Category category, LocalDateTime issuanceStartDate, LocalDateTime issuanceEndDate) {
        validate(name, discountAmount, minOrderAmount, issuanceStartDate, issuanceEndDate);
        this.id = id;
        this.name = name;
        this.discountAmount = discountAmount;
        this.minOrderAmount = minOrderAmount;
        this.category = category;
        this.issuanceStartDate = issuanceStartDate;
        this.issuanceEndDate = issuanceEndDate;
    }

    public Coupon(String name, int discountAmount, int minOrderAmount, Category category, LocalDateTime issuanceStartDate, LocalDateTime issuanceEndDate) {
        this(null, name, discountAmount, minOrderAmount, category, issuanceStartDate, issuanceEndDate);
    }

    private void validate(String name, int discountAmount, int minOrderAmount, LocalDateTime issuanceStartDate, LocalDateTime issuanceEndDate) {
        validateName(name);
        validateDiscount(discountAmount, minOrderAmount);
        validateIssuanceDate(issuanceStartDate, issuanceEndDate);
    }

    private void validateName(String name) {
        if (name == null || name.isEmpty() || name.length() > 30) {
            throw new IllegalArgumentException("Name cannot be null or more than 30");
        }
    }

    private void validateDiscount(int discountAmount, int minOrderAmount) {
        validateDiscountAmount(discountAmount);
        validateMinOrderAmount(minOrderAmount);
        validateDiscountRate(discountAmount, minOrderAmount);
    }

    private void validateDiscountAmount(int discountAmount) {
        if (discountAmount < 1000 || discountAmount > 10000) {
            throw new IllegalArgumentException("Discount Amount cannot be less than 1,000 or more than 10,000");
        }
        if (discountAmount % 500 != 0) {
            throw new IllegalArgumentException("Discount Amount must be multiple of 500");
        }
    }

    private void validateMinOrderAmount(int minOrderAmount) {
        if (minOrderAmount < 5000 || minOrderAmount > 100000) {
            throw new IllegalArgumentException("Minimum Order Amount cannot be less than 5,000 or more than 100,000");
        }
    }

    private void validateDiscountRate(int discountAmount, int minOrderAmount) {
        double discountRate = (double) 100 * discountAmount / minOrderAmount;
        if (discountRate < 3 || discountRate > 20) {
            throw new IllegalArgumentException("Discount Rate cannot be less than 3% or more than 20%");
        }
    }

    private void validateIssuanceDate(LocalDateTime issuanceStartDate, LocalDateTime issuanceEndDate) {
        if (!issuanceEndDate.isEqual(issuanceStartDate) && !issuanceEndDate.isAfter(issuanceStartDate)) {
            throw new IllegalArgumentException("Issuance End Date cannot be earlier than Issuance Start Date");
        }
    }
}
