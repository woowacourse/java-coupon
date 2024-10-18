package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    private static final long MINIMUM_DISCOUNT = 5000L;
    private static final long MAXIMUM_DISCOUNT = 10000L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PRICE", nullable = false)
    private long price;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    public Order(final long price, final Category category) {
        validatePrice(price);
        this.price = price;
        this.category = category;
    }

    private void validatePrice(final long amount) {
        validateMinimum(amount);
        validateMaximum(amount);
    }

    private void validateMinimum(final long amount) {
        if (amount < MINIMUM_DISCOUNT) {
            throw new IllegalArgumentException("주문 금액은 최소 %d 이상이어야합니다.".formatted(MINIMUM_DISCOUNT));
        }
    }

    private void validateMaximum(final long amount) {
        if (amount > MAXIMUM_DISCOUNT) {
            throw new IllegalArgumentException("주문 금액은 최대 %d 이하이어야합니다.".formatted(MAXIMUM_DISCOUNT));
        }
    }
}
