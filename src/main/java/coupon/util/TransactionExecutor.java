package coupon.util;

import java.util.function.Supplier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionExecutor {

    @Transactional
    public <T> T executeOnWriter(Supplier<T> supplier) {
        return supplier.get();
    }
}
