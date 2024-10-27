package coupon.coupon.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Order {

    private static final int MIN = 5000;
    private static final int MAX = 100_000;

    @Column(nullable = false)
    private int amount;
    //TODO 자료형 수정

    public Order(int amount) {
        validateAmount(amount);
        this.amount = amount;
    }

    private void validateAmount(int amount) {
        if (amount < MIN || amount > MAX) {
            throw new IllegalArgumentException("Order amount must be between " + MIN + " and " + MAX);
        }
    }
}
