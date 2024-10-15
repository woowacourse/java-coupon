package coupon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class MemberCoupon {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private Long couponId;
    private Long memberId;
    private boolean used;
    private LocalDateTime issuedAt;

    public MemberCoupon() {
    }

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
}
