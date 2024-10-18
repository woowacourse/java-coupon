package coupon.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Embedded
    private IssuancePeriod issuancePeriod;

    public Coupon(int discountAmount, int minimumOrderAmount) {
        this.name = new Name("쿠폰 이름");
        this.discountAmount = new DiscountAmount(discountAmount);
        this.minimumOrderAmount = new MinimumOrderAmount(minimumOrderAmount);
        this.issuancePeriod = new IssuancePeriod(LocalDateTime.now(), LocalDateTime.now());
    }
}
