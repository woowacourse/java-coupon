package coupon.coupon.domain;

import coupon.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class UserCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Coupon coupon;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private boolean isUsed;

    @Column(nullable = false)
    private LocalDate issueDate;

    public boolean isExpired(){
        return LocalDate.now().isAfter(issueDate.plusDays(7));
    }
}
