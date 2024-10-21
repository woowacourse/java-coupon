package coupon.coupon.domain;

import coupon.BaseEntity;
import coupon.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "member_coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "used", nullable = false)
    private boolean used;

    @Column(name = "issued_date", nullable = false)
    private LocalDateTime issedDate;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;
}
