package coupon.mock;

import java.util.function.Supplier;

import coupon.util.TransactionExecutor;

public class FakeTransactionExecutor<T> implements TransactionExecutor<T> {

    @Override
    public T exec(final Supplier<T> logic) {
        return logic.get();
    }
}
