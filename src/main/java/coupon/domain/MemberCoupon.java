package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private Boolean isUsed = false;

    @Column(nullable = false)
    private LocalDateTime issuedOn;

    @Column(nullable = false)
    private LocalDateTime expiresOn;

    public MemberCoupon(Coupon coupon, Member member, LocalDateTime issuedOn, LocalDateTime expiresOn) {
        this.coupon = coupon;
        this.member = member;
        this.issuedOn = issuedOn;
        this.expiresOn = expiresOn;
    }
}

