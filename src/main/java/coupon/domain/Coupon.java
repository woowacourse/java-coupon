package coupon.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "COUPON")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CouponName name;

    @Embedded
    private Discount discount;

    @Embedded
    private Period period;

    @OneToOne
    @JoinColumn(name = "ORDER_ID", referencedColumnName = "ID")
    private Order order;

    public Coupon(final CouponName name, final Discount discount, final Period period, final Order order) {
        this.name = name;
        this.discount = discount;
        this.period = period;
        this.order = order;
    }

    public String getCouponName() {
        return name.getName();
    }

    public long getDiscountAmount() {
        return discount.getAmount();
    }

    public LocalDateTime getStartAt() {
        return period.getStartAt();
    }

    public LocalDateTime getEndAt() {
        return period.getEndAt();
    }
}
