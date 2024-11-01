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
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    private boolean isUsed;

    private LocalDateTime createdAt;

    private LocalDateTime expiredAt;

    public static MemberCoupon create(Member member, Coupon coupon) {
        LocalDate createdDate = LocalDate.now();
        LocalDateTime createdAt = LocalDateTime.of(createdDate, LocalTime.MIDNIGHT);

        return new MemberCoupon(member, coupon, false, createdAt, createdAt.plusDays(7L));
    }

    public MemberCoupon(Member member, Coupon coupon, boolean isUsed, LocalDateTime createdAt, LocalDateTime expiredAt) {
        this.member = member;
        this.coupon = coupon;
        this.isUsed = isUsed;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }
}
