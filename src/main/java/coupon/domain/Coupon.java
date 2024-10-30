package coupon.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@NoArgsConstructor
public class Coupon {

    public static final int MIN_DISCOUNT_RATE = 3;
    public static final int MAX_DISCOUNT_RATE = 20;
    public static final int PRICE_UNIT = 500;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", columnDefinition = "varchar(30)", nullable = false)
    private String name;

    @Column(name = "category", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Column(name = "discount_amount")
    @Embedded
    private DiscountAmount discountAmount;

    @Column(name = "minimum_order_price")
    private MinimumOrderPrice minimumOrderPrice;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @CreationTimestamp
    @Column(name = "issue_started_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime issueStartedAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @CreationTimestamp
    @Column(name = "issue_ended_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime issueEndedAt;

    public Coupon(String name, Category category, int discountAmount, int minimumOrderPrice,
                  LocalDateTime issueStartedAt, LocalDateTime issueEndedAt) {
        validateDiscountRate(discountAmount, minimumOrderPrice);
        validateIssueDate(issueStartedAt, issueEndedAt);
        this.name = name;
        this.category = category;
        this.discountAmount = new DiscountAmount(discountAmount);
        this.minimumOrderPrice = new MinimumOrderPrice(minimumOrderPrice);
        this.issueStartedAt = issueStartedAt;
        this.issueEndedAt = issueEndedAt;
    }

    private void validateIssueDate(LocalDateTime issueStartedAt, LocalDateTime issueEndedAt) {
        if (issueEndedAt.isBefore(issueStartedAt)) {
            throw new IllegalArgumentException("발급끝시간은 발급 시작시간보다 앞설 수 없습니다.");
        }
    }

    private void validateDiscountRate(int discountAmount, int minimumOrderPrice) {
        int discountRate = (int) (((double) discountAmount / (double) minimumOrderPrice) * 100);
        if (discountRate < MIN_DISCOUNT_RATE || discountRate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException(
                    String.format("할인률은 %d퍼이상 %d이하 여야합니다.", MIN_DISCOUNT_RATE, MAX_DISCOUNT_RATE)
            );
        }
    }
}
