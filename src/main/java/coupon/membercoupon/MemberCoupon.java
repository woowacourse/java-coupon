package coupon.membercoupon;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Getter
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private long couponId;

    @Column(nullable = false)
    private long memberId;

    @Column(nullable = false)
    private boolean isUsed;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    public MemberCoupon(long couponId, long memberId) {
        this.couponId = couponId;
        this.memberId = memberId;
        this.isUsed = false;
        this.issuedAt = LocalDateTime.now();
        this.expiredAt = issuedAt.plusDays(6);
    }
}
