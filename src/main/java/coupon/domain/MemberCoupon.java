package coupon.domain;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MemberCoupon {

    private static final int EXPIRATION_DURATION = 6;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    private Coupon coupon;

    @ManyToOne(fetch = LAZY, optional = false)
    private Member member;

    private boolean used;

    @Column(nullable = true)
    private LocalDate issueDateTime;

    @Column(nullable = true)
    private LocalDate expirationDateTime;

    public MemberCoupon(Coupon coupon, Member member, LocalDate issueDateTime) {
        this.coupon = coupon;
        this.member = member;
        this.used = false;
        this.issueDateTime = issueDateTime;
        this.expirationDateTime = issueDateTime.plusDays(EXPIRATION_DURATION);
    }
}
