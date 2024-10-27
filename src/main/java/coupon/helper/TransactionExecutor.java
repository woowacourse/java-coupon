package coupon.helper;

import java.util.function.Supplier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionExecutor {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> T executeNewTransaction(Supplier<T> supplier) {
        return supplier.get();
    }
}
