package coupon.util;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class NewTransactionExecutor {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> T execute(TransactionCallback<T> callback) {
        return callback.execute();
    }

    @FunctionalInterface
    public interface TransactionCallback<T> {
        T execute();
    }
}
