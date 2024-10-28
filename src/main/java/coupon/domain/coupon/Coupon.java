package coupon.domain.coupon;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import coupon.domain.payment.Payment;
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
    private CouponName couponName;

    @Embedded
    private Discount discount;

    @Embedded
    private Period period;

    @Transient
    private long memberId;

    @ManyToOne
    @JoinColumn(name = "PAYMENT_ID", referencedColumnName = "ID")
    private Payment payment;

    public Coupon(final long id, final Coupon coupon, final long memberId) {
        this(coupon.couponName, coupon.discount, coupon.period, coupon.payment);
        this.id = id;
        this.memberId = memberId;
    }

    public Coupon(final CouponName couponName, final Discount discount, final Period period, final Payment payment) {
        this.couponName = couponName;
        this.discount = discount;
        this.period = period;
        this.payment = payment;
    }

    public String getCouponName() {
        return couponName.getName();
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

    public boolean isSameMemberId(final long memberId) {
        return this.memberId == memberId;
    }

    public boolean isSameName(final String couponName) {
        return getCouponName().equals(couponName);
    }
}
