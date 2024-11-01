package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    private static final int VALID_DAYS = 6;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long couponId;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private boolean isUsed = false;

    @Column(nullable = false, columnDefinition = "TIMESTAMP(6)")
    private LocalDateTime issuedAt;

    @Column(nullable = false, columnDefinition = "TIMESTAMP(6)")
    private LocalDateTime expiredAt;

    public MemberCoupon(Long couponId, Long memberId, LocalDateTime issuedAt) {
        this(null, couponId, memberId, false, issuedAt, calculateExpiredAt(issuedAt));
    }

    private static LocalDateTime calculateExpiredAt(LocalDateTime issuedAt) {
        return issuedAt.plusDays(VALID_DAYS)
                .with(LocalTime.MAX)
                .withNano(999999000);
    }
}
