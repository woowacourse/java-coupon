package coupon.coupon.entity;

import coupon.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "member_coupon")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCouponEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_id", nullable = false)
    private Long couponId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "used", nullable = false)
    private boolean used;

    @Column(name = "issued_date", nullable = false)
    private LocalDate issedDate;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    public MemberCouponEntity(Long memberId, Long couponId, boolean used, LocalDate issedDate) {
        this.memberId = memberId;
        this.couponId = couponId;
        this.used = used;
        this.issedDate = issedDate;
        this.expiryDate = issedDate.plusDays(7);
    }
}
