package coupon.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    private Integer discount;

    private Integer discountRate;

    private Integer minOrderPrice;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR")
    private Category category;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime issuableFrom;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime issuableTo;

    public Coupon(String name, Integer discount, Integer discountRate, Integer minOrderPrice, Category category,
                  LocalDateTime issuableFrom, LocalDateTime issuableTo) {
        validateName(name);
        validateDiscount(discount);
        validateDiscountRate(discountRate);
        validateMinOrderPrice(minOrderPrice);
        validateIssuable(issuableFrom, issuableTo);
        this.name = name;
        this.discount = discount;
        this.discountRate = discountRate;
        this.minOrderPrice = minOrderPrice;
        this.category = category;
        this.issuableFrom = issuableFrom;
        this.issuableTo = issuableTo;
    }

    public Coupon(String name, Integer discount, Integer minOrderPrice, Category category,
                  LocalDateTime issuableFrom, LocalDateTime issuableTo) {
        this(name, discount, discount * 100 / minOrderPrice, minOrderPrice, category, issuableFrom, issuableTo);
    }

    protected Coupon() {}

    private void validateName(String name) {
        if (name == null || name.length() > 30) {
            throw new IllegalArgumentException("잘못된 이름입니다: " + name);
        }
    }

    private void validateDiscount(Integer discount) {
        // 1000 <= x <= 10000, unit : 500
        if (discount < 1000 || discount > 10000) {
            throw new IllegalArgumentException("할인 금액 오류입니다: " + discount);
        }
        if (discount % 500 != 0) {
            throw new IllegalArgumentException("할인 금액 단위 오류입니다: " + discount);
        }
    }

    private void validateDiscountRate(Integer discountRate) {
        if (discountRate < 3 || discountRate > 20) {
            throw new IllegalArgumentException("할인률 오류입니다: " + discountRate);
        }
    }

    private void validateMinOrderPrice(Integer minOrderPrice) {
        if (minOrderPrice < 5_000 || minOrderPrice > 100_000) {
            throw new IllegalArgumentException("최소 주문 금액 오류입니다: " + minOrderPrice);
        }
    }

    private void validateIssuable(LocalDateTime issuableFrom, LocalDateTime issuableTo) {
        if (issuableFrom.isEqual(issuableTo) || issuableFrom.isAfter(issuableTo)) {
            throw new IllegalArgumentException("발급 가능 날짜 오류입니다: " + issuableFrom + " ~ " + issuableTo);
        }
    }

    @JsonIgnore
    public boolean issuable() {
        LocalDate today = LocalDate.now();
        LocalDate issuableDateFrom = issuableFrom.toLocalDate();
        LocalDate issuableDateTO = issuableTo.toLocalDate();

        return !today.isBefore(issuableDateFrom) && !today.isAfter(issuableDateTO);
    }
}
