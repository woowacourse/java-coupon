package coupon.membercoupon.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import coupon.infrastructure.audit.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class MemberCoupon extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "coupon_id", nullable = false)
    private Long couponId;

    @Column(name = "used", nullable = false)
    private Boolean used;

    @Column(name = "expiration_date", nullable = false)
    private ExpirationDate expirationDate;

    public MemberCoupon(Long memberId, Long couponId, Boolean used, LocalDate expirationDate) {
        this.memberId = memberId;
        this.couponId = couponId;
        this.used = used;
        this.expirationDate = new ExpirationDate(expirationDate);
    }
}
