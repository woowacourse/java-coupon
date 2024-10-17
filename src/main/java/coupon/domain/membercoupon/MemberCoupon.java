package coupon.domain.membercoupon;

import coupon.domain.coupon.Coupon;
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

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "is_active", columnDefinition = "BOOLEAN")
    private boolean isActive;

    @Embedded
    private UsagePeriod usagePeriod;

    public MemberCoupon(Coupon coupon, Member member, boolean isActive, UsagePeriod usagePeriod) {
        this(null, coupon, member, isActive, usagePeriod);
    }

    public MemberCoupon(Long id, Coupon coupon, Member member, boolean isActive, UsagePeriod usagePeriod) {
        this.id = id;
        this.coupon = coupon;
        this.member = member;
        this.isActive = isActive;
        this.usagePeriod = usagePeriod;
    }
}
