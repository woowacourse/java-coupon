package coupon.domain.membercoupon;

import java.time.LocalDateTime;
import java.time.LocalTime;
import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Coupon coupon;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    private Boolean isUsed;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    public MemberCoupon(
            Long id,
            Coupon coupon,
            Member member,
            Boolean isUsed,
            LocalDateTime createdAt
    ) {
        validateExpiration(createdAt);
        this.id = id;
        this.coupon = coupon;
        this.member = member;
        this.isUsed = isUsed;
        this.createdAt = createdAt;
        this.expiresAt = getExpiresAt(createdAt);
    }

    private void validateExpiration(LocalDateTime createdAt) {
        LocalDateTime expiresAt = getExpiresAt(createdAt);
        if (LocalDateTime.now().isAfter(expiresAt)) {
            throw new IllegalArgumentException("만료된 쿠폰입니다.");
        }
    }

    private LocalDateTime getExpiresAt(LocalDateTime createdAt) {
        return createdAt.plusDays(7).with(LocalTime.MAX);
    }
}
