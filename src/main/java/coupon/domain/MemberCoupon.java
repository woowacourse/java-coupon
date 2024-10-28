package coupon.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "member_coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    private static final int EXPIRATION_DAYS = 6;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "coupon_id")
    private long couponId;

    @Column(name = "member_id")
    private long memberId;

    @Column(name = "is_used")
    private boolean isUsed;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    public MemberCoupon(Long id, long couponId, long memberId, boolean isUsed, LocalDateTime issuedAt) {
        this.id = id;
        this.couponId = couponId;
        this.memberId = memberId;
        this.isUsed = isUsed;
        this.issuedAt = issuedAt;
        this.expiresAt = initExpiresAt(issuedAt);
    }

    public MemberCoupon(long couponId, long memberId, LocalDateTime issuedAt) {
        this.couponId = couponId;
        this.memberId = memberId;
        this.isUsed = false;
        this.issuedAt = issuedAt;
        this.expiresAt = initExpiresAt(issuedAt);
    }

    private static LocalDateTime initExpiresAt(LocalDateTime issuedAt) {
        return issuedAt.plusDays(EXPIRATION_DAYS).with(LocalTime.MAX);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public void use() {
        if (isUsed) {
            throw new IllegalArgumentException("이미 사용한 쿠폰입니다. couponId : " + couponId);
        }
        if (isExpired()) {
            throw new IllegalArgumentException("만료된 쿠폰입니다. couponId : " + couponId);
        }
        isUsed = true;
    }
}
