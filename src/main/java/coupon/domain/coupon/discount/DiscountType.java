package coupon.domain.coupon.discount;

import lombok.Getter;

@Getter
public enum DiscountType {
    PERCENT {
        @Override
        public DiscountPolicy createDiscountPolicy(int minDiscountPercent, int maxDiscountPercent) {
            return new PercentDiscountPolicy(minDiscountPercent, maxDiscountPercent);
        }
    };

    public abstract DiscountPolicy createDiscountPolicy(int minDiscountRange, int maxDiscountRange);
}
