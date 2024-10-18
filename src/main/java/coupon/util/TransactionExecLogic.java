package coupon.util;

@FunctionalInterface
public interface TransactionExecLogic<T> {

    T execute();
}
