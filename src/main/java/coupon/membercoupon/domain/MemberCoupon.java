package coupon.membercoupon.domain;

import coupon.coupon.domain.Coupon;
import coupon.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(value = EnumType.STRING)
    private UsageStatus usageStatus;

    @Embedded
    private UsablePeriod usablePeriod;

    public MemberCoupon(Coupon coupon, Member member, LocalDate issuanceDate) {
        this.coupon = coupon;
        this.member = member;
        this.usageStatus = UsageStatus.NON_USE;
        this.usablePeriod = new UsablePeriod(issuanceDate);
    }
}
