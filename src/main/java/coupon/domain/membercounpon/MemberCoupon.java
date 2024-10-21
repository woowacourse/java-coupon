package coupon.domain.membercounpon;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    private static final int VALID_PERIOD = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long couponId;

    private Long memberId;

    private boolean isUsed;

    private LocalDateTime createdAt;

    private LocalDateTime expiredAt;

    public MemberCoupon(
            Long couponId,
            Long memberId,
            boolean isUsed,
            LocalDateTime createdAt
    ) {
        this.couponId = couponId;
        this.memberId = memberId;
        this.isUsed = isUsed;
        this.createdAt = createdAt;
        this.expiredAt = LocalDateTime.of(createdAt.toLocalDate().plusDays(VALID_PERIOD), LocalTime.MAX);
    }
}

