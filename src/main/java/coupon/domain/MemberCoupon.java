package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long couponId; // coupon ID만 저장하도록 수정

    @ManyToOne
    private Member member;

    private boolean used;

    private LocalDateTime issuedAt;

    private LocalDateTime expiredAt;

    public MemberCoupon(Long couponId, Member member) {
        this.couponId = couponId;
        this.member = member;
        this.used = false;
        this.issuedAt = LocalDateTime.now();
        this.expiredAt = issuedAt.plusDays(7).withHour(23).withMinute(59).withSecond(59).withNano(999999000);
    }
}


