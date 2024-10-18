package coupon;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import lombok.Getter;

@Entity
@Getter
public class MemberCoupon {
    @Id
    private Long id;

    @ManyToOne
    private Coupon coupon;

    @ManyToOne
    private Member member;

    @Column(nullable = false)
    private boolean used;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

}
