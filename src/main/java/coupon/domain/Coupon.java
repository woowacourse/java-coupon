package coupon.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static coupon.domain.utils.CouponValidator.validate;

@Entity
@Getter
public class Coupon {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private final Long id;
    private final String name;
    private final Integer discountAmount;
    private final Integer minOrderAmount;

    @Enumerated(EnumType.STRING)
    private final Category category;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Coupon(Long id, String name, Integer discountAmount, Integer minOrderAmount, Category category, LocalDate startDate, LocalDate endDate) {
        validate(name, discountAmount, minOrderAmount, category, startDate, endDate);
        this.id = id;
        this.name = name;
        this.discountAmount = discountAmount;
        this.minOrderAmount = minOrderAmount;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Coupon(String name, Integer discountAmount, Integer minOrderAmount, Category category, LocalDate startDate, LocalDate endDate) {
        this(null, name, discountAmount, minOrderAmount, category, startDate, endDate);
    }

    public boolean isSameDate() {
        return startDate.isEqual(endDate);
    }

    public boolean canBeGranted(LocalDateTime requestTime) {
        LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.of(23, 59, 59, 999999000));

        return !requestTime.isBefore(startDateTime) && !requestTime.isAfter(endDateTime);
    }
}
