package coupon.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "member_coupon")
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private long memberId;

    @Column(name = "coupon_id", nullable = false)
    private long couponId;

    @Column(name = "used", nullable = false)
    private boolean used;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    public MemberCoupon(Member member, Coupon coupon) {
        this(
                null,
                member.getId(),
                coupon.getId(),
                false,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(6).withHour(23).withMinute(59).withSecond(59).withNano(999999)
        );
    }
}
