package coupon.domain.membercoupon;

import java.time.LocalDateTime;
import coupon.domain.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_id", nullable = false)
    private Long couponId;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "is_active", columnDefinition = "BOOLEAN", nullable = false)
    private boolean isActive;

    @Embedded
    private UsagePeriod usagePeriod;

    public MemberCoupon(Long couponId, Member member, LocalDateTime issueDate) {
        this(null, couponId, member, true, new UsagePeriod(issueDate));
    }

    public MemberCoupon(Long couponId, Member member, UsagePeriod usagePeriod) {
        this(null, couponId, member, true, usagePeriod);
    }

    public MemberCoupon(Long id, Long couponId, Member member, boolean isActive, UsagePeriod usagePeriod) {
        this.id = id;
        this.couponId = couponId;
        this.member = member;
        this.isActive = isActive;
        this.usagePeriod = usagePeriod;
    }
}
