package coupon.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Getter;

@Getter
public class MemberCoupon {

    private static final int EXPIRATION_DAYS = 6;

    private Long id;
    private long couponId;
    private long memberId;
    private boolean isUsed;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;

    public MemberCoupon(Long id, long couponId, long memberId, boolean isUsed, LocalDateTime issuedAt) {
        this.id = id;
        this.couponId = couponId;
        this.memberId = memberId;
        this.isUsed = isUsed;
        this.issuedAt = issuedAt;
        this.expiresAt = initExpiresAt(issuedAt);
    }

    public MemberCoupon(long couponId, long memberId, boolean isUsed, LocalDateTime issuedAt) {
        this.couponId = couponId;
        this.memberId = memberId;
        this.isUsed = isUsed;
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
