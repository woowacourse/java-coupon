package coupon.entity;

import coupon.domain.coupon.Category;
import coupon.domain.coupon.CouponDomain;
import coupon.domain.coupon.UserStorageCoupon;
import coupon.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@ToString
@Getter
@Table(name = "coupon")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "discount_price", nullable = false)
    private int discountPrice;

    @Column(name = "minimum_order_price", nullable = false)
    private int minimumOrderPrice;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "issue_end_date", nullable = false)
    private LocalDate issueEndDate;

    public Coupon(CouponDomain coupon) {
        this(null,
                coupon.getCouponName(),
                coupon.getDiscountPrice(),
                coupon.getMinimumPrice(),
                coupon.getCategory(),
                coupon.getStartDate(),
                coupon.getEndDate()
        );
    }

    public UserStorageCoupon toUserStorageCoupon() {
        return UserStorageCoupon.of(this);
    }

    public int issueDayDeadLine() {
        return issueEndDate.compareTo(LocalDate.now());
    }

    public boolean notEndDateCoupon() {
        LocalDate now = LocalDate.now();
        return now.isBefore(getIssueEndDate());
    }
}
