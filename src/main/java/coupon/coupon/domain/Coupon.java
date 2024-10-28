package coupon.coupon.domain;

import coupon.coupon.exception.CouponErrorMessage;
import coupon.coupon.exception.CouponException;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
public class Coupon {

    public static final int MAX_NAME_LENGTH = 30;
    private static final int MIN_MINIMUM_ORDER_AMOUNT = 5000;
    private static final int MAX_MINIMUM_ORDER_AMOUNT = 100000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Embedded
    private Discount discount;

    private int minimumOrderAmount;

    @Enumerated(EnumType.STRING)
    private Category category;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    protected Coupon() {
    }

    public Coupon(String name, int price, int minimumOrderAmount, Category category, LocalDateTime startAt, LocalDateTime endAt) {
        validateName(name);
        validateMinimumOrderAmount(minimumOrderAmount);
        validateUsablePeriod(startAt, endAt);
        this.name = name;
        this.discount = new Discount(price, minimumOrderAmount);
        this.minimumOrderAmount = minimumOrderAmount;
        this.category = category;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    private void validateName(String name) {
        if(name.isEmpty()) {
            throw new CouponException(CouponErrorMessage.NAME_CANNOT_EMPTY);
        }
        if(name.length() > MAX_NAME_LENGTH) {
            throw new CouponException(CouponErrorMessage.NAME_TOO_LONG);
        }
    }

    private void validateMinimumOrderAmount(int minimumOrderAmount) {
        if(minimumOrderAmount < MIN_MINIMUM_ORDER_AMOUNT || minimumOrderAmount > MAX_MINIMUM_ORDER_AMOUNT) {
            throw new CouponException(CouponErrorMessage.INVALID_MINIMUM_ORDER_AMOUNT);
        }
    }

    private void validateUsablePeriod(LocalDateTime startAt, LocalDateTime endAt) {
        if (startAt.isAfter(endAt)) {
            throw new CouponException(CouponErrorMessage.START_CANNOT_BE_AFTER_END);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon = (Coupon) o;
        return Objects.equals(id, coupon.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
