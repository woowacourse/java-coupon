package coupon.coupon.repository;

import coupon.coupon.domain.IssuedCoupon;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
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

    public static IssuedCouponEntity from(long memberId, long couponId, IssuedCoupon coupon) {
        return new IssuedCouponEntity(
                null,
                couponId,
                memberId,
                coupon.isUsed(),
                coupon.getIssuedAt(),
                coupon.getExpiredDate()
        );
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_id", nullable = false)
    private Long couponId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "used", nullable = false, columnDefinition = "BOOLEAN")
    private boolean used;

    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt;

    @Column(name = "expirationDate", nullable = false)
    private LocalDate expirationDate;
}
