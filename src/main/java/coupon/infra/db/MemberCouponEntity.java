package coupon.infra.db;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_coupon")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long couponId;

    private Long memberId;

    private boolean use;

    private LocalDate startDate;

    private LocalDate endDate;

    public MemberCouponEntity(Long couponId, Long memberId, boolean use, LocalDate startDate, LocalDate endDate) {
        this(null, couponId, memberId, use, startDate, endDate);
    }
}
