package coupon.util;

import java.util.function.Function;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class WriteDBConnector {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T, R> R apply(Function<T, R> function, T t) {
        return function.apply(t);
    }
}
