package coupon.coupon.domain;

import coupon.coupon.exception.CouponErrorMessage;
import coupon.coupon.exception.CouponException;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class Coupon {

    private static final int MAX_NAME_LENGTH = 30;
    private static final int MIN_MINIMUM_ORDER_AMOUNT = 5000;
    private static final int MAX_MINIMUM_ORDER_AMOUNT = 100000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Embedded
    private Discount discount;
    private int minimumOrderAmount;
    @Enumerated(EnumType.STRING)
    private Category category;
    private LocalDate startAt;
    private LocalDate endAt;

    protected Coupon() {

    }

    public Coupon(String name, int price, int minimumOrderAmount, Category category, LocalDate startAt, LocalDate endAt) {
        validateName(name);
        this.name = name;
        this.discount = new Discount(price, minimumOrderAmount);
        validateMinimumOrderAmount(minimumOrderAmount);
        this.minimumOrderAmount = minimumOrderAmount;
        this.category = category;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    private void validateName(String name) {
        if(name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Name is too long");
        }
    }

    private void validateMinimumOrderAmount(int minimumOrderAmount) {
        if(minimumOrderAmount < MIN_MINIMUM_ORDER_AMOUNT || minimumOrderAmount > MAX_MINIMUM_ORDER_AMOUNT) {
            throw new CouponException(CouponErrorMessage.INVALID_MINIMUM_ORDER_AMOUNT);
        }
    }
}
