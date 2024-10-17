package coupon.util;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionExecutor {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> T executeWithNewTransaction(TransactionalMethod<T> method) {
        return method.execute();
    }
}
