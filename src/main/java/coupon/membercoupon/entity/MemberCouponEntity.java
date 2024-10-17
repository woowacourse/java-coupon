package coupon.membercoupon.entity;

import coupon.membercoupon.domain.MemberCoupon;
import jakarta.persistence.*;

@Entity
public class MemberCouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long couponId;

    private Long memberId;

    @Embedded
    private MemberCoupon memberCoupon;
}
