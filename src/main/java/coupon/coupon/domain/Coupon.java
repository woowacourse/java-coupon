package coupon.coupon.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Getter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Name name;

    private int discountPrice;

    private int minimumOrderPrice;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public Coupon() {
    }

    public Coupon(String name, int discountPrice, int minimumOrderPrice,
                  Category category, LocalDateTime startTime, LocalDateTime endTime) {
        this.name = new Name(name);
        this.discountPrice = discountPrice;
        this.minimumOrderPrice = minimumOrderPrice;
        this.category = category;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
