package coupon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int discountAmount;
    private int minimumOrderPrice;

    public Coupon(int discountAmount, int minimumOrderPrice) {
        this.discountAmount = discountAmount;
        this.minimumOrderPrice = minimumOrderPrice;
    }
}
