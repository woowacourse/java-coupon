package coupon.infra.db;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int discountMoney;

    private int minOrderMoney;

    private String category;

    private LocalDate startDate;

    private LocalDate endDate;

    public CouponEntity(
            String name,
            int discountMoney,
            int minOrderMoney,
            String category,
            LocalDate startDate,
            LocalDate endDate
    ) {
        this.name = name;
        this.discountMoney = discountMoney;
        this.minOrderMoney = minOrderMoney;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
