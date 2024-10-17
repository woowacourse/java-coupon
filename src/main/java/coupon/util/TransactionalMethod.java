package coupon.util;

@FunctionalInterface
public interface TransactionalMethod<T> {

    T execute();
}
