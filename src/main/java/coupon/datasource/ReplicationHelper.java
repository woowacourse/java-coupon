package coupon.datasource;

import coupon.util.NoExceptionCallable;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ReplicationHelper {

    private final ApplicationContext applicationContext;

    @Transactional(readOnly = true)
    public <T> Optional<T> get(NoExceptionCallable<Optional<T>> callable) {
        Optional<T> call = callable.call();
        if (call.isPresent()) {
            return call;
        }
        ReplicationHelper selfProxy = applicationContext.getBean(ReplicationHelper.class);
        return selfProxy.getByWriter(callable);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> Optional<T> getByWriter(NoExceptionCallable<Optional<T>> callable) {
        return callable.call();
    }
}
