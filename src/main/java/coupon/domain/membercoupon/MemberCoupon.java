package coupon.domain.membercoupon;

import coupon.domain.BaseEntity;
import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @ManyToOne(optional = false)
    @JoinColumn(name = "coupon_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Coupon coupon;

    private boolean isUsed = false;

    private LocalDateTime issuedStartDate;

    private LocalDateTime issuedEndDate;

    public MemberCoupon(Member member, Coupon coupon) {
        this.member = member;
        this.coupon = coupon;
        this.isUsed = false;
        this.issuedStartDate = LocalDateTime.now();
        this.issuedEndDate = calculateExpireDateTime(issuedStartDate);
    }

    private LocalDateTime calculateExpireDateTime(LocalDateTime issuedStartDate) {
        return issuedStartDate.plusDays(7)
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999999000);
    }
}
