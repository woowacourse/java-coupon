package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_coupon_id")
    private Long id;

    private Long couponId;

    private Long memberId;

    @Column(nullable = false)
    private Boolean isUsed = false;

    @Column(nullable = false)
    private LocalDateTime issuedOn;

    @Column(nullable = false)
    private LocalDateTime expiresOn;

    public MemberCoupon(Coupon coupon, Member member, LocalDateTime issuedOn, LocalDateTime expiresOn) {
        this.couponId = coupon.getId();
        this.memberId = member.getId();
        this.issuedOn = issuedOn;
        this.expiresOn = expiresOn;
    }
}

