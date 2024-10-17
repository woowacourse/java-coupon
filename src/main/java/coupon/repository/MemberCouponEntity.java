package coupon.repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberCouponEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "COUPON", nullable = false)
    private CouponEntity coupon;

    @Column(name = "MEMBER_ID", nullable = false)
    private long memberId;

    @Column(name = "IS_USED", nullable = false)
    private boolean isUsed;

    @Column(name = "ISSUED_AT", nullable = false)
    private LocalDateTime issuedAt;

    @Column(name = "EXPIRES_AT", nullable = false)
    private LocalDateTime expiresAt;


    public MemberCouponEntity(final CouponEntity coupon, final long memberId) {
        this.coupon = coupon;
        this.memberId = memberId;
        this.isUsed = false;
        this.issuedAt = LocalDateTime.now();
        this.expiresAt = issuedAt.plusDays(7).toLocalDate().atTime(LocalTime.MAX);
    }

    // TODO 롬복 사용해서 이퀄 앤 해시 코드 생각하기

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final MemberCouponEntity that)) {
            return false;
        }
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
