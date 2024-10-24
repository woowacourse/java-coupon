package coupon.entity;

import coupon.domain.Coupon;
import coupon.domain.CouponCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupon")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(30)")
    private String name;

    @Column(name = "discount_amount", columnDefinition = "int")
    private Integer discountAmount;

    @Column(name = "minimum_order_amount", columnDefinition = "int")
    private Integer minimumOrderAmount;

    @Column(name = "category", columnDefinition = "varchar(20)")
    @Enumerated(value = EnumType.STRING)
    private CouponCategory category;

    @Column(name = "issue_started_at", columnDefinition = "datetime")
    private LocalDateTime issueStartedAt;

    @Column(name = "issue_ended_at", columnDefinition = "datetime")
    private LocalDateTime issueEndedAt;

    public CouponEntity(Coupon coupon) {
        this.name = coupon.getName();
        this.discountAmount = coupon.getDiscountAmount();
        this.minimumOrderAmount = coupon.getMinimumOrderAmount();
        this.category = coupon.getCategory();
        this.issueStartedAt = coupon.getIssueStartedAt();
        this.issueEndedAt = coupon.getIssueEndedAt();
    }

    public Coupon toDomain() {
        return new Coupon(id, name, discountAmount, minimumOrderAmount, category, issueStartedAt, issueEndedAt);
    }
}
