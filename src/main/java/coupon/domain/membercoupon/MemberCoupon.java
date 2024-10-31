package coupon.domain.membercoupon;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "member_coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_id")
    private long couponId;

    @Column(name = "member_id")
    private long memberId;

    private boolean used;

    @Column(name = "issued_at", nullable = false, columnDefinition = "TIMESTAMP(6)")
    private LocalDateTime issuedAt;

    @Column(name = "expires_at", columnDefinition = "TIMESTAMP(6)")
    private LocalDateTime expiresAt;

    public MemberCoupon(Long id, long couponId, long memberId, boolean used, LocalDateTime issuedAt) {
        this.id = id;
        this.couponId = couponId;
        this.memberId = memberId;
        this.used = used;
        this.issuedAt = issuedAt;
        this.expiresAt = issuedAt.plusDays(6).with(LocalTime.MAX);
    }

    public MemberCoupon(long couponId, long memberId) {
        this(null, couponId, memberId, false, LocalDateTime.now());
    }
}
