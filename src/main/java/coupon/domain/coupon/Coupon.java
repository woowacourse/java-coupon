package coupon.domain.coupon;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Embedded
    private Name name;

    @NonNull
    @Embedded
    private DiscountAmount discountAmount;

    @NonNull
    @Embedded
    private MinimumOrderAmount minimumOrderAmount;

    @NonNull
    @Embedded
    private IssuancePeriod issuancePeriod;
}
