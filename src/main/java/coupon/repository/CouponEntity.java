package coupon.repository;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import coupon.domain.Coupon;
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

    @Column(name = "ISSUED_START_DATE_TIME", nullable = false)
    private LocalDateTime issuedStartDateTime;

    @Column(name = "ISSUED_END_DATE_TIME", nullable = false)
    private LocalDateTime issuedEndDateTime;

    public CouponEntity(final Coupon coupon) {
        this.name = coupon.getName().getValue();
        this.discountAmount = coupon.getDiscountAmount().getValue();
        this.minOrderAmount = coupon.getMinOrderAmount().getAmount();
        this.issuedStartDateTime = coupon.getIssuancePeriod().getStartDateTime();
        this.issuedEndDateTime = coupon.getIssuancePeriod().getEndDateTime();
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
