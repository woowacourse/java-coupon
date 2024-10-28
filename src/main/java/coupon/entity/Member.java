package coupon.entity;

import coupon.common.ErrorConstant;
import coupon.common.exception.CouponException;
import coupon.domain.member.MemberDomain;
import coupon.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "member")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    public static final int MAX_ISSUE_COUPON = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "member")
    private List<MemberCoupon> memberCoupons;


    public Member(MemberDomain memberDomain) {
        this(null,
                memberDomain.getMemberName(),
                List.of());
    }

    public MemberCoupon issueCoupon(Long couponId, LocalDateTime issuedAt) {
        validateCanIssueMember();
        return MemberCoupon.issueOf(this, couponId, issuedAt);
    }

    public MemberDomain toDomain() {
        return new MemberDomain(name);
    }

    public void validateCanIssueMember() {
        if (memberCoupons.size() > MAX_ISSUE_COUPON) {
            throw new CouponException(ErrorConstant.ISSUED_COUPON_MAX);
        }
    }

    public List<MemberCoupon> getUnusedMemberCoupons() {
        return memberCoupons.stream()
                .filter(memberCoupon -> !memberCoupon.getIsUsed())
                .toList();
    }
}
