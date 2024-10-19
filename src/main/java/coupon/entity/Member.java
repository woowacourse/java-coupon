package coupon.entity;

import coupon.domain.member.MemberDomain;
import coupon.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "member")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public Member(MemberDomain memberDomain) {
        this(null,
                memberDomain.getMemberName());
    }

    public MemberCoupon issueCoupon(Long couponId, LocalDateTime issuedAt) {
        return MemberCoupon.issueOf(this, couponId, issuedAt);
    }
}
