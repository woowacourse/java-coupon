package coupon.coupon.domain;

import coupon.coupon.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coupon extends BaseEntity {

    private static final int MAX_NAME_LENGTH = 30;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @NonNull
    private String name;

    private int discountAmount;

    private int discountRate;

    private int minOrderAmount;

    @Enumerated(EnumType.STRING)
    private Category category;

    public Coupon(
            Long id, String name,
            int discountAmount, int discountRate,
            int minOrderAmount, Category category,
            LocalDateTime startDate, LocalDateTime endDate
            ) {
        validateName(name);
        this.id = id;
        this.name = name;
        this.discountAmount = discountAmount;
        this.discountRate = discountRate;
        this.minOrderAmount = minOrderAmount;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("쿠폰 이름은 반드시 존재해야 합니다.");
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("쿠폰 이름은 30자 이하여야 합니다.");
        }
    }
}
