package coupon.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberCoupon> memberCoupons = new ArrayList<>();

    public Member() {
    }

    public void addCoupon(Coupon coupon) {
        MemberCoupon memberCoupon = new MemberCoupon(coupon, this);
        memberCoupons.add(memberCoupon);
    }

    public Long getId() {
        return id;
    }

    public List<MemberCoupon> getMemberCoupons() {
        return memberCoupons;
    }
}
