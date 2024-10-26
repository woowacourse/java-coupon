package coupon.global;

import java.util.function.Supplier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ReplicationLagFallback {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> T readFromWriter(Supplier<T> supplier) {
        return supplier.get();
    }
}
