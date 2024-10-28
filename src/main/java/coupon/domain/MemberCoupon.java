package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @JoinColumn(name = "coupon_id")
    private Long couponId;

    @Column(name = "issued_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime issuedAt;

    @Column(name = "use_ended_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime useEndedAt;

    @Column(name = "used", columnDefinition = "BOOLEAN")
    private boolean used;

    @Column(name = "used_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime usedAt;

    public MemberCoupon(Member member, Coupon coupon) {
        this(
                null,
                member,
                coupon.getId(),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7).withHour(0).withMinute(0).withSecond(0),
                false,
                null
        );
    }
}

