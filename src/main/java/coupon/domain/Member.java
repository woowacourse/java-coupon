package coupon.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberCoupon> memberCoupons = new ArrayList<>();

    public Member() {
    }

    public MemberCoupon addCoupon(Coupon coupon) {
        MemberCoupon memberCoupon = new MemberCoupon(coupon.getId(), this);
        memberCoupons.add(memberCoupon);
        return memberCoupon;
    }

    public Long getId() {
        return id;
    }

    public List<MemberCoupon> getMemberCoupons() {
        return memberCoupons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
