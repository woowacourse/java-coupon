package coupon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MemberCoupon {

    private static final int EXPIRATION_DAYS = 7;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private Long couponId;
    private Long memberId;
    private boolean used;
    private LocalDateTime issuedAt;

    public MemberCoupon(Long couponId, Long memberId, boolean used, LocalDateTime issuedAt) {
        this(null, couponId, memberId, used, issuedAt);
    }

    public MemberCoupon(Long id, Long couponId, Long memberId, boolean used, LocalDateTime issuedAt) {
        this.id = id;
        this.couponId = couponId;
        this.memberId = memberId;
        this.used = used;
        this.issuedAt = issuedAt;
    }

    public LocalDateTime getExpiredAt() {
        return issuedAt.plusDays(EXPIRATION_DAYS)
                .with(LocalTime.MAX)
                .truncatedTo(ChronoUnit.MICROS);
    }
}
