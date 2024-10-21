package coupon.domain.coupon;

public class TestDiscountPolicy extends BasicDiscountPolicy {

    private final int discountMoney;

    private final int minOrderMoney;

    public TestDiscountPolicy(int discountMoney, int minOrderMoney) {
        super(discountMoney, minOrderMoney);
        this.discountMoney = discountMoney;
        this.minOrderMoney = minOrderMoney;
    }

    @Override
    public int getDiscountMoney() {
        return discountMoney;
    }

    @Override
    public int getMinOrderMoney() {
        return minOrderMoney;
    }
}
