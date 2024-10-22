package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    /*
    회원에게 발급된 쿠폰은 발급일 포함 7일 동안 사용 가능하다. 만료 일의 23:59:59.999999 까지 사용할 수 있다.
     */
    private static final int EXPIRATION_DAYS = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "coupon_id", nullable = false)
    private Long couponId;

    @Column(name = "used", columnDefinition = "boolean")
    private Boolean used;

    @Column(name = "issued_at", columnDefinition = "datetime(6)")
    private LocalDateTime issuedAt;

    @Column(name = "expired_at", columnDefinition = "datetime(6)")
    private LocalDateTime expiredAt;

    public MemberCoupon(Member member, Coupon coupon) {
        this.memberId = member.getId();
        this.couponId = coupon.getId();
        this.used = false;
        this.issuedAt = LocalDateTime.now();
        this.expiredAt = issuedAt.plusDays(EXPIRATION_DAYS).minusDays(1).with(LocalTime.MAX)
                .truncatedTo(ChronoUnit.MICROS);
    }
}
