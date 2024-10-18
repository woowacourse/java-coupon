package coupon.membercoupon.domain;

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

    private long couponId;

    private long memberId;

    @Enumerated(value = EnumType.STRING)
    private UsageStatus usageStatus;

    @Embedded
    private UsablePeriod usablePeriod;

    public MemberCoupon(long couponId, long memberId, LocalDate issuanceDate) {
        this.couponId = couponId;
        this.memberId = memberId;
        this.usageStatus = UsageStatus.NON_USE;
        this.usablePeriod = new UsablePeriod(issuanceDate);
    }
}
