package coupon.membercoupon.repository;

import coupon.membercoupon.domain.MemberCoupon;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Table(name = "member_coupon")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_id", nullable = false)
    private Long couponId;

    @Column(name = "memberEntity_id", nullable = false)
    private Long memberId;

    @Column(name = "available", nullable = false)
    private Boolean available;

    @Column(name = "start", nullable = false)
    private LocalDateTime start;

    @Column(name = "end", nullable = false)
    private LocalDateTime end;

    public MemberCouponEntity(Long couponId, Long memberId, MemberCoupon memberCoupon) {
        this(null, couponId, memberId, memberCoupon.isAvailable(), memberCoupon.getAvailableStartDate(),
                memberCoupon.getAvailableEndDate());
    }
}
