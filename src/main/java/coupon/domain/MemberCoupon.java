package coupon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    private static final int DEFAULT_AVAILABLE_DAYS = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long couponId;

    private long memberId;

    private boolean used = false;

    private LocalDateTime issuedAt;

    private LocalDateTime expiresAt;

    public MemberCoupon(long couponId, long memberId) {
        this.couponId = couponId;
        this.memberId = memberId;
        this.issuedAt = LocalDateTime.now();
        this.expiresAt = calculateExpiresAt(this.issuedAt);
    }

    private LocalDateTime calculateExpiresAt(LocalDateTime issuedAt) {
        return issuedAt.toLocalDate()
                .plusDays(DEFAULT_AVAILABLE_DAYS)
                .atStartOfDay()
                .minusNanos(1);
    }
}
