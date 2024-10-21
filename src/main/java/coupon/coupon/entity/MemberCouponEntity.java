package coupon.coupon.entity;

import coupon.BaseEntity;
import coupon.member.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "member_coupon")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCouponEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "coupon_id", nullable = false)
    private CouponEntity coupon;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity member;

    @Column(name = "used", nullable = false)
    private boolean used;

    @Column(name = "issued_date", nullable = false)
    private LocalDateTime issedDate;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;
}
