package coupon.application;

import java.util.function.Supplier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataSourceHelper {

    @Transactional
    public void executeInWriter(Runnable runnable) {
        runnable.run();
    }

    @Transactional
    public <T> T executeInWriter(Supplier<T> supplier) {
        return supplier.get();
    }
}
