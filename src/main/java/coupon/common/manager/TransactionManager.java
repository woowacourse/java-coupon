package coupon.common.manager;

import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@NoArgsConstructor
public class TransactionManager {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> T newTransaction(Supplier<T> supplier) {
        return supplier.get();
    }
}
