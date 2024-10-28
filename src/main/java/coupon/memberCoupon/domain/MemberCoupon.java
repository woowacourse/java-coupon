package coupon.memberCoupon.domain;

import coupon.coupon.domain.Coupon;
import coupon.member.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private boolean isUsed;

    private LocalDateTime createdAt;

    private LocalDateTime expiredAt;

    public static MemberCoupon create(Coupon coupon, Member member) {
        LocalDate createdDate = LocalDate.now();
        LocalDateTime createdAt = LocalDateTime.of(createdDate, LocalTime.MIDNIGHT);

        return new MemberCoupon(coupon, member, false, createdAt, createdAt.plusDays(7L));
    }

    public MemberCoupon(Coupon coupon, Member member, boolean isUsed, LocalDateTime createdAt, LocalDateTime expiredAt) {
        this.coupon = coupon;
        this.member = member;
        this.isUsed = isUsed;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }
}
