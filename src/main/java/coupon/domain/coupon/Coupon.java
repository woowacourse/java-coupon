package coupon.domain.coupon;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
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

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private int discountAmount;

    private int discountRate;

    private int minOrderAmount;

    private Category category;

    private LocalDateTime issuePeriod;

    public Coupon(String name, int discountAmount, int discountRate, int minOrderAmount, Category category, LocalDateTime issuePeriod) {
        this.name = name;
        this.discountAmount = discountAmount;
        this.discountRate = discountRate;
        this.minOrderAmount = minOrderAmount;
        this.category = category;
        this.issuePeriod = issuePeriod;
    }
}
