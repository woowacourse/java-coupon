package coupon.coupon.domain;

import coupon.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long couponId;

    @Column(nullable = false)
    private boolean used;

    @Column(nullable = false, updatable = false, columnDefinition = "DATETIME(6)")
    private LocalDateTime issueAt;

    @Column(nullable = false, updatable = false, columnDefinition = "DATETIME(6)")
    private LocalDateTime expiredAt;

    public MemberCoupon(Long memberId, Long couponId) {
        this.memberId = memberId;
        this.couponId = couponId;
        this.used = false;
        this.issueAt = LocalDateTime.now();
        this.expiredAt = issueAt.plusDays(7).withHour(23).withMinute(59).withSecond(59).withNano(999999000);
    }
}
