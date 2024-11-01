package coupon;

import java.util.function.Supplier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataSourceRoutingSupport {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> T changeToWrite(Supplier<T> action) {
        return action.get();
    }
}
