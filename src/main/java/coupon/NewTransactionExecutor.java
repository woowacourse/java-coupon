package coupon;

import java.util.function.Supplier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NewTransactionExecutor<T> {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public T execute(Supplier<T> supplier) {
        return supplier.get();
    }
}
