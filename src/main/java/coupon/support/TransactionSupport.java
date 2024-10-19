package coupon.support;

import java.util.function.Supplier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionSupport {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> T withNewTransaction(Supplier<T> method) {
        return method.get();
    }
}
