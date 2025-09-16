package coupon.marketing.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;

@Entity
@Table(name = "monthly_member_benefit")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonthlyMemberBenefit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "year", columnDefinition = "SMALLINT")
    private Year year;
    @Column(name = "month", columnDefinition = "SMALLINT")
    private Month month;
    @Column(name = "coupon_discount_amount")
    private int couponDiscountAmount;
    @Column(name = "created_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime createdAt;
    @Column(name = "modified_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime modifiedAt;

    public static MonthlyMemberBenefit create(Long memberId, Year year, Month month) {
        MonthlyMemberBenefit monthlyMemberBenefit = new MonthlyMemberBenefit();
        monthlyMemberBenefit.memberId = memberId;
        monthlyMemberBenefit.year = year;
        monthlyMemberBenefit.month = month;
        monthlyMemberBenefit.couponDiscountAmount = 0;
        monthlyMemberBenefit.createdAt = LocalDateTime.now();
        monthlyMemberBenefit.modifiedAt = monthlyMemberBenefit.createdAt;

        return monthlyMemberBenefit;
    }

    public void increaseCouponDiscountAmount(int discountAmount) {
        couponDiscountAmount += discountAmount;
        modifiedAt = LocalDateTime.now();
    }
}
