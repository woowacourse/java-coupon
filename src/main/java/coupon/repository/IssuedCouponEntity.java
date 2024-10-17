package coupon.repository;

import coupon.domain.coupon.IssuedCoupon;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "issued_coupon")
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class IssuedCouponEntity {

    public static IssuedCouponEntity from(long memberId, IssuedCoupon coupon) {
        return new IssuedCouponEntity(
                null,
                CouponEntity.from(coupon.getCoupon()),
                memberId,
                coupon.isUsed(),
                coupon.getIssuedAt(),
                coupon.getExpiredAt()
        );
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coupon_id", nullable = false)
    private CouponEntity coupon;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "used", nullable = false, columnDefinition = "BOOLEAN")
    private boolean used;

    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;
}
