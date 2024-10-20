package coupon.util;

@FunctionalInterface
public interface NoExceptionCallable<T> {
    T call();
}
