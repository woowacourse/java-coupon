package coupon.domain.member;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "membercoupon")
public class MemberCoupon {

    private static final int EXPIRATION_DAYS = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_id")
    private long couponId;

    @Column(name = "member_id")
    private long memberId;

    @Column(name = "is_used")
    private Boolean isUsed;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;


    public MemberCoupon(long couponId, long memberId) {
        this.couponId = couponId;
        this.isUsed = false;
        this.createdAt = LocalDateTime.now();
        this.expiredAt = LocalDateTime.now().plusDays(EXPIRATION_DAYS);
        this.memberId = memberId;
    }
}
