package coupon.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "published_coupon")
@Getter
public class PublishedCoupon {

    private static final int EXPIRATION_TERM = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Coupon coupon;

    @Column(name = "is_used")
    private boolean isUsed;

    @Column(name = "issuance_date")
    private LocalDateTime issuanceDate;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    protected PublishedCoupon() {
    }

    public PublishedCoupon(Long id, Member member, Coupon coupon, boolean isUsed, LocalDateTime issuanceDate) {
        validateIssuance(coupon, issuanceDate);
        this.id = id;
        this.member = member;
        this.coupon = coupon;
        this.isUsed = isUsed;
        this.issuanceDate = issuanceDate;
        this.expirationDate = issuanceDate.plusDays(EXPIRATION_TERM).with(LocalTime.MAX);
    }

    public PublishedCoupon(Member member, Coupon coupon, boolean isUsed, LocalDateTime expirationDate) {
        this(null, member, coupon, isUsed, expirationDate);
    }

    private void validateIssuance(Coupon coupon, LocalDateTime issuanceDate) {
        if (coupon.getIssuanceStartDate().isAfter(issuanceDate)) {
            throw new IllegalArgumentException("Issuance start date cannot be after issuance date");
        }
        if (coupon.getIssuanceEndDate().isBefore(issuanceDate)) {
            throw new IllegalArgumentException("Issuance end date cannot be before issuance date");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PublishedCoupon that)) return false;
        return isUsed == that.isUsed &&
                Objects.equals(id, that.id) &&
                Objects.equals(member, that.member) &&
                Objects.equals(coupon, that.coupon) &&
                Objects.equals(issuanceDate, that.issuanceDate) &&
                Objects.equals(expirationDate, that.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, member, coupon, isUsed, issuanceDate, expirationDate);
    }
}
