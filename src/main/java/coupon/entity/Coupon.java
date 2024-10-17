package coupon.entity;

import coupon.CouponException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coupon {

    private static final int MAX_NAME_LENGTH = 30;
    private static final int MIN_MINIMUM_ORDER = 5000;
    private static final int MAX_MINIMUM_ORDER = 100000;
    private static final int MIN_DISCOUNT = 1000;
    private static final int MAX_DISCOUNT = 10000;
    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;
    private static final int PERCENT_MULTIPLIER = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int discount;

    @Column(name = "minimum_order")
    private int minimumOrder;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar")
    private Category category;

    private LocalDate start;

    private LocalDate end;

    public Coupon(String name, int discount, int minimumOrder, Category category, LocalDate start, LocalDate end) {
        validate(name, discount, minimumOrder, start, end);
        this.name = name;
        this.discount = discount;
        this.minimumOrder = minimumOrder;
        this.category = category;
        this.start = start;
        this.end = end;
    }

    private void validate(String name, int discount, int minimumOrder, LocalDate start, LocalDate end) {
        validateName(name);
        validateMinimumOrder(minimumOrder);
        validateDiscount(discount, minimumOrder);
        validateDate(start, end);
    }

    private void validateName(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new CouponException("name is too long");
        }
    }

    private void validateMinimumOrder(int minimumOrder) {
        if (minimumOrder < MIN_MINIMUM_ORDER || minimumOrder > MAX_MINIMUM_ORDER) {
            throw new CouponException("minimumOrder is out of range");
        }
    }

    private void validateDiscount(int discount, int minimumOrder) {
        if (discount < MIN_DISCOUNT || discount > MAX_DISCOUNT) {
            throw new CouponException("discount is out of range");
        }
        int rate = (discount * PERCENT_MULTIPLIER) / minimumOrder;
        if (rate < MIN_DISCOUNT_RATE || rate > MAX_DISCOUNT_RATE) {
            throw new CouponException("discount rate is out of range");
        }
    }

    private void validateDate(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new CouponException("Start date cannot be after end date");
        }
    }
}
