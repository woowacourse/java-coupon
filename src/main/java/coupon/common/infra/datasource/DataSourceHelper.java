package coupon.common.infra.datasource;

import java.util.function.Supplier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataSourceHelper {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void executeInWriter(Runnable runnable) {
        runnable.run();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> T executeInWriter(Supplier<T> supplier) {
        return supplier.get();
    }
}
