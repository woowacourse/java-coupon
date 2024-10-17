package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int salePrice;

    public Coupon(int salePrice) {
        this.id = null;
        this.salePrice = salePrice;
    }

    protected Coupon() {
    }

    public int getSalePrice() {
        return salePrice;
    }
}
