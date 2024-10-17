package coupon.domain.coupon;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int discountPrice;

    private int minOrderPrice;

    @Enumerated(EnumType.STRING)
    private Category category;

    private LocalDate issueStartDate;

    private LocalDate issueEndDate;
}
