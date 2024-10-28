package coupon.domain.member;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "membercoupon")
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_id")
    private long couponId;

    @Column(name = "is_used")
    private Boolean isUsed;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @ManyToOne
    private Member member;

    public MemberCoupon(long couponId,
                        Boolean isUsed,
                        LocalDateTime createdAt,
                        LocalDateTime expiredAt,
                        Member member) {
        this.couponId = couponId;
        this.isUsed = isUsed;
        validate(createdAt, expiredAt);
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.member = member;
    }

    private void validate(LocalDateTime createdAt, LocalDateTime expiredAt) {
        if (expiredAt.isBefore(createdAt)) {
            throw new IllegalArgumentException("만료 날짜는 생성날짜 이전일 수 없습니다.");
        }
    }

    @Override
    public String toString() {
        return "MemberCoupon{" +
                "id=" + id +
                ", couponId=" + couponId +
                ", isUsed=" + isUsed +
                ", createdAt=" + createdAt +
                ", expiredAt=" + expiredAt +
                ", member=" + member +
                '}';
    }
}
