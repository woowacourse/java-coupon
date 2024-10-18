package coupon.util;

public interface TransactionExecutor<T> {

    T exec(final TransactionExecLogic<T> logic);
}
