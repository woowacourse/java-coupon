package coupon;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_coupon")
@NoArgsConstructor
public class MemberCoupon {

    @Id
    private Long id;

    @OneToOne
    private Member member;

    @ManyToOne Coupon coupon;
}
