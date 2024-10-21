package coupon.config;

import java.util.function.Supplier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataSourceHelper {

    @Transactional(readOnly = false)
    public <T> T executeOnWrite(Supplier<T> supplier) {
        return supplier.get();
    }
}
