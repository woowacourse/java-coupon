package coupon.domain.coupon;

import coupon.domain.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
*회원에게 발급하는 쿠폰의 이름, 할인 금액, 최소 주문 금액 등 쿠폰의 설정에 해당하는 내용을 관리한다. 다음과 같은 제약사항을 갖는다.
발급 기간
시작일은 종료일보다 이전이어야 한다. 시작일과 종료일이 같다면, 해당 일에만 발급할 수 있는 쿠폰이 된다.
시작일 00:00:00.000000 부터 발급할 수 있다.
종료일 23:59:59.999999 까지 발급할 수 있다.
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CouponName couponName;

    @Embedded
    private DiscountAmount discountAmount;

    @Embedded
    private MinimumOrderAmount minimumOrderAmount;

    @Embedded
    private DiscountRate discountRate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private IssueDuration issueDuration;

    public Coupon(
            CouponName couponName,
            DiscountAmount discountAmount,
            MinimumOrderAmount minimumOrderAmount,
            Category category,
            IssueDuration issueDuration
    ) {
        this.couponName = couponName;
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.discountRate = new DiscountRate(discountAmount, minimumOrderAmount);
        this.category = category;
        this.issueDuration = issueDuration;
    }
}
