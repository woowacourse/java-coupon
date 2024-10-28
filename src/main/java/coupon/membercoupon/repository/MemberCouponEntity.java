package coupon.membercoupon.repository;

import coupon.member.repository.MemberEntity;
import coupon.membercoupon.domain.MemberCoupon;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne(optional = false)
    private MemberEntity memberEntity;

    @Column(name = "available", nullable = false)
    private Boolean available;

    @Column(name = "start", nullable = false)
    private LocalDateTime start;

    @Column(name = "end", nullable = false)
    private LocalDateTime end;

    public MemberCouponEntity(Long couponId, MemberEntity memberEntity, MemberCoupon memberCoupon) {
        this(null, couponId, memberEntity, memberCoupon.isAvailable(), memberCoupon.getAvailableStartDate(),
                memberCoupon.getAvailableEndDate());
    }
}
