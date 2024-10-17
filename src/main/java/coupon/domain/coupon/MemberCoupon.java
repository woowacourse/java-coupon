package coupon.domain.coupon;

import coupon.domain.BaseEntity;
import coupon.domain.member.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "member_coupon")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon extends BaseEntity {

    private static final int COUPON_USABLE_DAYS = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Member member;

    @ManyToOne(optional = false)
    private Coupon coupon;

    private boolean isUsed = false;

    private LocalDateTime issuedAt;

    private LocalDateTime expireAt;

    public MemberCoupon(Member member, Coupon coupon) {
        this.member = member;
        this.coupon = coupon;
        this.isUsed = false;
        this.issuedAt = LocalDateTime.now();
        this.expireAt = calcExpireAt(issuedAt);
    }

    private LocalDateTime calcExpireAt(LocalDateTime issuedAt) {
        return issuedAt.plusDays(COUPON_USABLE_DAYS)
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999999);
    }
}
