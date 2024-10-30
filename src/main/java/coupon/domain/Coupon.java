package coupon.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "coupon")
@Getter
public class Coupon {

    private static final int MAX_NAME_LENGTH = 30;

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
        validateName(name);
        this.id = id;
        this.name = name;
        this.discountAmount = discountAmount;
        this.minOrderAmount = minOrderAmount;
        this.category = category;
        this.issuanceStartDate = issuanceStartDate;
        this.issuanceEndDate = issuanceEndDate;
    }

    public Coupon(String name, Discount discount, Category category, CouponIssuanceDate issuanceDate) {
        this(null, name, discount.getDiscountAmount(), discount.getMinOrderAmount(), category, issuanceDate.getIssuanceStartDate(), issuanceDate.getIssuanceEndDate());
    }

    private void validateName(String name) {
        if (name == null || name.isEmpty() || name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(String.format("Name cannot be null or more than %d", MAX_NAME_LENGTH));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coupon coupon)) return false;
        return discountAmount == coupon.discountAmount &&
                minOrderAmount == coupon.minOrderAmount &&
                Objects.equals(id, coupon.id) &&
                Objects.equals(name, coupon.name) &&
                category == coupon.category &&
                Objects.equals(issuanceStartDate, coupon.issuanceStartDate) &&
                Objects.equals(issuanceEndDate, coupon.issuanceEndDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, discountAmount, minOrderAmount, category, issuanceStartDate, issuanceEndDate);
    }
}
