package coupon.coupon.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class Coupon {

    private  static final int MAX_NAME_LENGTH = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Discount discount;
    private int minimumOrderAmount;
    @Enumerated(EnumType.STRING)
    private Category category;
    private LocalDate startAt;
    private LocalDate endAt;

    protected Coupon() {

    }

    public Coupon(String name, int price, int minimumOrderAmount, Category category, LocalDate startAt, LocalDate endAt) {
        validateName(name);
        this.name = name;
        this.discount = new Discount(price, minimumOrderAmount);
        this.minimumOrderAmount = minimumOrderAmount;
        this.category = category;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    private void validateName(String name) {
        if(name.length() <= MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Name is too long");
        }
    }
}
