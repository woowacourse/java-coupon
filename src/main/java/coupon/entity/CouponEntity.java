package coupon.entity;

import coupon.domain.coupon.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private long discountAmount;

    private long minOrderAmount;

    private int discountRange;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    private LocalDate issueStartDate;

    private LocalDate issueEndDate;

    public CouponEntity(String name, long discountAmount, long minOrderAmount, int discountRange,
                        Category category, LocalDate issueStartDate, LocalDate issueEndDate) {
        this.name = name;
        this.discountAmount = discountAmount;
        this.minOrderAmount = minOrderAmount;
        this.discountRange = discountRange;
        this.category = category;
        this.issueStartDate = issueStartDate;
        this.issueEndDate = issueEndDate;
    }
}
