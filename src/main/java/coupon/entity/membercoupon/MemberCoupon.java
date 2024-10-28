package coupon.entity.membercoupon;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private Long couponId;

    private boolean isUsed;

    @Embedded
    private MemberCouponPeriod couponPeriod;

    public MemberCoupon(Long memberId, Long couponId, boolean isUsed, LocalDate issuedAt) {
        this(null,
                memberId,
                couponId,
                isUsed,
                new MemberCouponPeriod(issuedAt)
        );
    }
}
