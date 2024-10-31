package coupon.coupon.domain;

import static lombok.AccessLevel.PROTECTED;

import coupon.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class UserCoupon {

    private static final int EXPIRED_LIMIT = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long couponId;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private boolean isUsed;

    @Column(nullable = false)
    private LocalDate issueDate;

    public UserCoupon(Long couponId, User user, boolean isUsed, LocalDate issueDate) {
        this.couponId = couponId;
        this.user = user;
        this.isUsed = isUsed;
        this.issueDate = issueDate;
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(issueDate.plusDays(EXPIRED_LIMIT));
    }
}
