package coupon.db;

import java.util.function.Supplier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionRouter {

    @Transactional
    public <T> T route(Supplier<T> supplier) {
        return supplier.get();
    }
}
