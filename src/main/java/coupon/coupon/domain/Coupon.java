package coupon.coupon.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Name name;

    @Embedded
    private DiscountAmount discountAmount;

    @Embedded
    private MinimumOrderAmount minimumOrderAmount;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public Coupon(String name, BigDecimal discountAmount, BigDecimal minimumOrderAmount,
                  Category category, LocalDateTime startTime, LocalDateTime endTime) {
        this.name = new Name(name);
        this.discountAmount = new DiscountAmount(discountAmount);
        this.minimumOrderAmount = new MinimumOrderAmount(minimumOrderAmount);
        this.category = category;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
