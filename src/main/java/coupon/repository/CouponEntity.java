package coupon.repository;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.CouponName;
import coupon.domain.DiscountAmount;
import coupon.domain.IssuancePeriod;
import coupon.domain.MinOrderAmount;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "COUPON")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DISCOUNT_AMOUNT", nullable = false)
    private Integer discountAmount;

    @Column(name = "MIN_ORDER_AMOUNT", nullable = false)
    private Integer minOrderAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY", nullable = false)
    private Category category;

    @Column(name = "ISSUED_START_DATE_TIME", nullable = false)
    private LocalDateTime issuedStartDateTime;

    @Column(name = "ISSUED_END_DATE_TIME", nullable = false)
    private LocalDateTime issuedEndDateTime;

    public CouponEntity(final Coupon coupon) {
        this.name = coupon.getName().getValue();
        this.discountAmount = coupon.getDiscountAmount().getValue();
        this.minOrderAmount = coupon.getMinOrderAmount().getAmount();
        this.category = coupon.getCategory();
        this.issuedStartDateTime = coupon.getIssuancePeriod().getStartDateTime();
        this.issuedEndDateTime = coupon.getIssuancePeriod().getEndDateTime();
    }

    public Coupon toDomain() {
        final CouponName couponName = new CouponName(getName());
        final DiscountAmount discountAmount = new DiscountAmount(getDiscountAmount());
        final MinOrderAmount minOrderAmount = new MinOrderAmount(getMinOrderAmount());
        final IssuancePeriod issuancePeriod = new IssuancePeriod(getIssuedStartDateTime().toLocalDate(),
                getIssuedEndDateTime().toLocalDate());
        final Category category = getCategory();
        return new Coupon(couponName, discountAmount, minOrderAmount, issuancePeriod, category);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final CouponEntity that)) {
            return false;
        }
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
