package coupon.util;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionExecutor<T> {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public T exec(final TransactionExecLogic<T> logic) {
        return logic.execute();
    }
}
