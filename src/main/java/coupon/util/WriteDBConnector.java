package coupon.util;

import java.util.function.Supplier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class WriteDBConnector {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> T apply(Supplier<T> function) {
        return function.get();
    }
}
