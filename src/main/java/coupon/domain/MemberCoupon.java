package coupon.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import coupon.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private Boolean isUsed;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    public MemberCoupon(Coupon coupon, Member member, LocalDateTime issuedAt) {
        validateIssuedAt(issuedAt);
        this.coupon = coupon;
        this.member = member;
        this.isUsed = false;
        this.issuedAt = issuedAt;
        this.expiresAt = calculateExpiryDate(issuedAt);
    }

    private void validateIssuedAt(LocalDateTime issuedAt) {
        if (Objects.isNull(issuedAt)) {
            throw new IllegalArgumentException("발급 일시는 반드시 존재해야 합니다.");
        }
    }

    private LocalDateTime calculateExpiryDate(LocalDateTime issuedAt) {
        return issuedAt.plusDays(6).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
    }
}
