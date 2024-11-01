package coupon.repository.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import coupon.domain.MemberCoupon;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEMBER_COUPON")
@Entity
public class MemberCouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MEMBER_ID", nullable = false)
    private Long memberId;

    @Column(name = "COUPON_ID", nullable = false)
    private Long couponId;

    @Column(name = "IS_USED", nullable = false)
    private boolean isUsed;

    @Column(name = "ISSUED_DATE", nullable = false)
    private LocalDateTime issuedDate;

    @Column(name = "EXPIRATION_DATE", nullable = false)
    private LocalDateTime expirationDate;

    public MemberCouponEntity(
            final Long memberId,
            final Long couponId,
            final boolean isUsed,
            final LocalDateTime issuedDate,
            final LocalDateTime expirationDate
    ) {
        this.couponId = couponId;
        this.memberId = memberId;
        this.isUsed = isUsed;
        this.issuedDate = issuedDate;
        this.expirationDate = expirationDate;
    }

    public static MemberCouponEntity toEntity(final MemberCoupon memberCoupon) {
        return new MemberCouponEntity(
                memberCoupon.getMemberId(),
                memberCoupon.getCouponId(),
                memberCoupon.isUsed(),
                memberCoupon.getIssuedDate(),
                memberCoupon.getExpirationDate()
        );
    }

    public MemberCoupon toDomain() {
        return new MemberCoupon(
                memberId,
                couponId,
                isUsed,
                issuedDate,
                expirationDate
        );
    }
}
