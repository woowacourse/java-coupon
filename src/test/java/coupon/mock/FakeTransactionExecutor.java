package coupon.mock;

import coupon.util.TransactionExecLogic;
import coupon.util.TransactionExecutor;

public class FakeTransactionExecutor<T> implements TransactionExecutor<T> {

    @Override
    public T exec(final TransactionExecLogic<T> logic) {
        return logic.execute();
    }
}
