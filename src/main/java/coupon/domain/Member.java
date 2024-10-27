package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private int couponCount = 0;

    @OneToMany(mappedBy = "member")
    private List<MemberCoupon> memberCoupons;

    public void incrementCouponCount() {
        if (couponCount >= 5) {
            throw new IllegalArgumentException("한 회원은 최대 5장의 쿠폰만 발급받을 수 있습니다.");
        }
        couponCount++;
    }

    public void decrementCouponCount() {
        if (couponCount > 0) {
            couponCount--;
        }
    }
}
