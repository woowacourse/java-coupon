package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long couponId;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Boolean isUsed;

    @Column(nullable = false)
    private LocalDateTime issuedDateTime;

    @Column(nullable = false)
    private LocalDateTime limitDateTime;

    public MemberCoupon(Long couponId, Long memberId, Boolean isUsed, LocalDateTime issuedDateTime) {
        validateIsExpired(issuedDateTime);
        this.couponId = couponId;
        this.memberId = memberId;
        this.isUsed = isUsed;
        this.issuedDateTime = issuedDateTime;
        this.limitDateTime = getExpiredDate(issuedDateTime);
    }

    private void validateIsExpired(LocalDateTime issuedDateTime) {
        LocalDateTime expiredDate = getExpiredDate(issuedDateTime);

        if (LocalDateTime.now().isAfter(expiredDate)) {
            throw new IllegalArgumentException("쿠폰이 만료되었습니다.");
        }
    }

    private LocalDateTime getExpiredDate(LocalDateTime issuedDateTime) {
        return issuedDateTime
                .plusDays(7L)
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999999000);
    }
}
