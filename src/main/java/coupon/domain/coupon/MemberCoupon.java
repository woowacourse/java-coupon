package coupon.domain.coupon;

import coupon.domain.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberCoupon {

    private static final int AVAILABLE_PERIOD_DAYS = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Coupon coupon;

    @ManyToOne(optional = false)
    private Member member;

    @Column(nullable = false)
    private boolean isUsed;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    public MemberCoupon(Coupon coupon, Member member, LocalDateTime issuedAt) {
        this.coupon = coupon;
        this.member = member;
        this.isUsed = false;
        this.issuedAt = issuedAt;
        this.expiredAt = issuedAt.plusDays(AVAILABLE_PERIOD_DAYS).truncatedTo(ChronoUnit.DAYS);
    }
}
