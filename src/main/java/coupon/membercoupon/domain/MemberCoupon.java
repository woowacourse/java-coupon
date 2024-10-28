package coupon.membercoupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Getter;

@Entity
@Getter
public class MemberCoupon {

    private static final int AVAILABLE_PERIOD = 6;
    private static final int MAX_NANO_SECOND = 999;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long couponId;

    private Long memberId;

    private Boolean isUsed;

    private LocalDateTime issuedAt;

    private LocalDateTime expiredAt;

    public MemberCoupon() {
    }

    public MemberCoupon(Long id, Long couponId, Long memberId, Boolean isUsed,
                        LocalDateTime issuedAt, LocalDateTime expiredAt) {
        this.id = id;
        this.couponId = couponId;
        this.memberId = memberId;
        this.isUsed = isUsed;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
    }

    public MemberCoupon(Long couponId, Long memberId, LocalDateTime issuedAt) {
        this(null, couponId, memberId, false, issuedAt,
                issuedAt.plusDays(AVAILABLE_PERIOD).with(LocalTime.MAX.minusNanos(MAX_NANO_SECOND)));
    }
}
