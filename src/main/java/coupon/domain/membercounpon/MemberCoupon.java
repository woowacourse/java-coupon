package coupon.domain.membercounpon;

import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    private static final int VALID_PERIOD = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private boolean isUsed;

    private LocalDateTime createdAt;

    private LocalDateTime expiredAt;

    public MemberCoupon(
            Coupon coupon,
            Member member
    ) {
        this.coupon = coupon;
        this.member = member;
        this.isUsed = false;
        this.createdAt = LocalDateTime.now();
        this.expiredAt = LocalDateTime.of(createdAt.toLocalDate().plusDays(VALID_PERIOD), LocalTime.MAX);
    }
}

