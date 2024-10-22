package coupon.util;

import java.util.function.Supplier;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProdTransactionExecutor<T> implements TransactionExecutor<T> {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public T exec(final Supplier<T> logic) {
        return logic.get();
    }
}
