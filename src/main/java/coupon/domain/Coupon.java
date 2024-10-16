package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    // 1000 <= x <= 10000, unit : 500
    private Integer discount;

    // 3% <= x <= 20%
    private Integer discountRate;

    // 5000 <= x <= 100000
    private Integer minOrderPrice;

    @Enumerated(EnumType.STRING)
    private Category category;

    private LocalDateTime issuableFrom;

    private LocalDateTime issuableTo;

    public Coupon(String name, Integer discount, Integer discountRate, Integer minOrderPrice, Category category,
                  LocalDateTime issuableFrom, LocalDateTime issuableTo) {
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
        this(name, discount, discount / minOrderPrice, minOrderPrice, category, issuableFrom, issuableTo);
    }

    protected Coupon() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getDiscount() {
        return discount;
    }

    public Integer getDiscountRate() {
        return discountRate;
    }

    public Integer getMinOrderPrice() {
        return minOrderPrice;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDateTime getIssuableFrom() {
        return issuableFrom;
    }

    public LocalDateTime getIssuableTo() {
        return issuableTo;
    }
}


/*
쿠폰
회원에게 발급하는 쿠폰의 이름, 할인 금액, 최소 주문 금액 등 쿠폰의 설정에 해당하는 내용을 관리한다. 다음과 같은 제약사항을 갖는다.
이름
이름은 반드시 존재해야 한다.
이름의 길이는 최대 30자 이하여야 한다.
할인 금액
할인 금액 제약
할인 금액은 1,000원 이상이어야 한다.
할인 금액은 10,000원 이하여야 한다.
할인 금액은 500원 단위로 설정할 수 있다.
할인율 제약
할인율은 할인금액 / 최소 주문 금액 식을 사용하여 계산하며, 소수점은 버림 한다.
예를 들어, 최소 주문 금액이 30,000원일 때 할인 금액이 1,000원이라면 쿠폰의 할인율은 3%(1000 / 30000 = 3.333...%)가 된다.
할인율은 3% 이상이어야 한다.
할인율은 20% 이하여야 한다.
최소 주문 금액
최소 주문 금액은 5,000원 이상이어야 한다.
최소 주문 금액은 100,000원 이하여야 한다.
카테고리
서비스의 카테고리는 다음과 같이 네 종류가 있고, 이 중 하나의 카테고리만 선택할 수 있다.
패션
가전
가구
식품
발급 기간
시작일은 종료일보다 이전이어야 한다. 시작일과 종료일이 같다면, 해당 일에만 발급할 수 있는 쿠폰이 된다.
시작일 00:00:00.000000 부터 발급할 수 있다.
종료일 23:59:59.999999 까지 발급할 수 있다.
 */
