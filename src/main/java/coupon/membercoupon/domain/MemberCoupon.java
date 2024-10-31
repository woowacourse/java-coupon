package coupon.membercoupon.domain;

import coupon.coupon.domain.Coupon;
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

    @JoinColumn(name = "member_id")
    private long memberId;

    @Enumerated(value = EnumType.STRING)
    private UsageStatus usageStatus;

    @Embedded
    private UsablePeriod usablePeriod;

    public MemberCoupon(Coupon coupon, long memberId, LocalDate issuanceDate) {
        this.coupon = coupon;
        this.memberId = memberId;
        this.usageStatus = UsageStatus.NON_USE;
        this.usablePeriod = new UsablePeriod(issuanceDate);
    }
}
