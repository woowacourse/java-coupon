package coupon.coupon.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    /**
     * 모든 쿠폰은 발급 일시부터 7일간 사용할 수 있다.
     */
    private static final int COUPON_USABLE_DAYS = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "member_id")
    private Long memberId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
    @Column(name = "issued_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime issuedAt;
    @Column(name = "use_ended_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime useEndedAt;
    @Column(name = "used", columnDefinition = "BOOLEAN")
    private boolean used;
    @Column(name = "used_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime usedAt;
    @Column(name = "modified_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime modifiedAt;

    public static MemberCoupon issue(Long memberId, Coupon coupon) {
        coupon.issue();

        MemberCoupon memberCoupon = new MemberCoupon();
        memberCoupon.memberId = memberId;
        memberCoupon.coupon = coupon;
        memberCoupon.issuedAt = LocalDateTime.now();
        memberCoupon.useEndedAt = memberCoupon.issuedAt.plusDays(COUPON_USABLE_DAYS);
        memberCoupon.modifiedAt = LocalDateTime.now();
        memberCoupon.used = false;

        return memberCoupon;
    }

    public void use() {
        if (this.used) {
            throw new IllegalStateException("이미 사용한 쿠폰입니다.");
        }
        this.coupon.use();

        this.used = true;
        this.usedAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }
}
