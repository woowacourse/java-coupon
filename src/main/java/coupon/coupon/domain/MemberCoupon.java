package coupon.coupon.domain;

import coupon.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_coupon")
@Getter
@NoArgsConstructor
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "owner", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member owner;

    @JoinColumn(name = "coupon", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Coupon coupon;

    public MemberCoupon(Long id, Member owner, Coupon coupon) {
        this.id = id;
        this.owner = owner;
        this.coupon = coupon;
    }

    public MemberCoupon(Member owner, Coupon coupon) {
        this(null, owner, coupon);
    }
}
