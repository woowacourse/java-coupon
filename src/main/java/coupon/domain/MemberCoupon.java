package coupon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @ManyToOne
    private Coupon coupon;

    @NotNull
    @ManyToOne
    private Member member;

    private boolean used = false;

    private LocalDateTime issuedAt;

    private LocalDateTime expiresAt;

    public MemberCoupon(Coupon coupon, Member member) {
        this.coupon = coupon;
        this.member = member;
        this.used = false;
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
