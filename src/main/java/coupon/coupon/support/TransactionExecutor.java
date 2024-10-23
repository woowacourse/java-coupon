package coupon.coupon.support;

import java.util.concurrent.Callable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionExecutor {

    @Transactional
    public <T> T executeByWriter(Callable<T> callable)  {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new IllegalStateException("트랜잭션 내에서 로직 실행에 실패했습니다. - " + e.getMessage());
        }
    }
}
