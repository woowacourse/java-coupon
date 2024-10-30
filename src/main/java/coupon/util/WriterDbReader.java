package coupon.util;

import java.util.function.Supplier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class WriterDbReader {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> T read(Supplier<T> supplier) {
        return supplier.get();
    }
}
