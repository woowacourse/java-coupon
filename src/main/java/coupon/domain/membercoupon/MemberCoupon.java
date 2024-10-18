package coupon.domain.membercoupon;

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
import lombok.NonNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long couponId;

    private long memberId;

    private boolean used;

    @NonNull
    private LocalDateTime issuedAt;

    private LocalDateTime expiresAt;

    public MemberCoupon(long couponId, long memberId) {
        this.couponId = couponId;
        this.memberId = memberId;
        this.used = false;
        this.issuedAt = LocalDateTime.now();
        this.expiresAt = issuedAt.plusDays(6).with(LocalTime.MAX);
    }
}
