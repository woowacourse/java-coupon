package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "member_coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    private static final int COUPON_USABLE_DAYS = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "coupon_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Coupon coupon;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(name = "used", nullable = false, columnDefinition = "boolean")
    private boolean used;

    @Column(name = "issued_at", columnDefinition = "datetime(6)")
    private LocalDateTime issuedAt;

    @Column(name = "use_ended_at", columnDefinition = "datetime(6)")
    private LocalDateTime useEndedAt;

    public MemberCoupon(Coupon coupon, Member member, boolean used) {
        LocalDateTime now = LocalDateTime.now();
        this.coupon = coupon;
        this.member = member;
        this.used = used;
        this.issuedAt = now;
        this.useEndedAt = now.plusDays(COUPON_USABLE_DAYS);
    }

    public boolean isAvailable(LocalDateTime time) {
        LocalDate endDate = useEndedAt.toLocalDate();
        LocalDate otherDate = time.toLocalDate();
        return endDate.isBefore(otherDate) || endDate.equals(otherDate);
    }

    public void loadCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Long getCouponId() {
        return coupon.getId();
    }
}
