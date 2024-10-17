package coupon.domain.coupon;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Embedded
    private Name name;
    @Embedded
    private MinimumOrderAmount minimumOrderAmount;
    @Embedded
    private DiscountAmount discountAmount;
    private Category category;
    @Embedded
    private PeriodOfIssuance periodOfIssuance;

    public Coupon(String name, int minimumOrderAmount, int discountAmount, String category,
                  LocalDate startDate, LocalDate endDate) {
        this(null, new Name(name), new MinimumOrderAmount(minimumOrderAmount),
                new DiscountAmount(discountAmount, minimumOrderAmount), Category.valueOf(category),
                new PeriodOfIssuance(startDate, endDate));
    }
}
