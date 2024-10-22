package coupon.util;

import java.util.function.Supplier;

public interface TransactionExecutor<T> {

    T exec(Supplier<T> logic);
}
